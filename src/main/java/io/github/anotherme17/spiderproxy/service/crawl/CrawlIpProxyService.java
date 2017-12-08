package io.github.anotherme17.spiderproxy.service.crawl;

import io.github.anotherme17.base.http.ProxyUtil;
import io.github.anotherme17.okhttp3util.OkClient;
import io.github.anotherme17.okhttp3util.interceptor.HttpLoggingInterceptor;
import io.github.anotherme17.okhttp3util.util.DeviceInfoUtil;
import io.github.anotherme17.spiderproxy.common.CrawlConstants;
import io.github.anotherme17.spiderproxy.po.proxy.IpProxyPo;
import io.github.anotherme17.spiderproxy.service.SpringBeanService;
import io.github.anotherme17.spiderproxy.service.proxy.ProxyService;
import io.github.anotherme17.spiderproxy.service.wash.base.BaseWash;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lirenhao
 * date: 2017/12/4 上午10:59
 */
@Slf4j
@Service
public class CrawlIpProxyService {

    public static final int PROXY_MAX_SIZE = 1000;

    private boolean crawling = false;

    @Autowired
    private ProxyService proxyService;

    public void updateIpProxyPool() {
        try {
            if (crawling)
                return;

            crawling = true;

            List<IpProxyPo> oldProxys = proxyService.getAll();
            oldProxys = oldProxys
                    .stream()
                    .filter(proxy -> {
                        long speed = ProxyUtil.checkProxy(proxy.getIp(), proxy.getPort());
                        if (speed < 0) {
                            proxyService.deleteById(proxy.getId());
                            return false;
                        } else {
                            proxy.setSpeed(speed);
                            proxyService.update(proxy);
                            return true;
                        }
                    })
                    .sequential()
                    .collect(Collectors.toList());

            if (oldProxys.size() >= PROXY_MAX_SIZE)
                return;


            OkClient client = new OkClient(HttpLoggingInterceptor.Level.BODY, null, DeviceInfoUtil.randomUserAgent());

            Flowable.fromIterable(CrawlConstants.CRAWL_MAP.entrySet())
                    .parallel()
                    .map(entry -> {
                        BaseWash wash = SpringBeanService.getBean(entry.getValue(), BaseWash.class);
                        if (wash == null)
                            return null;

                        try {
                            String content = client.builder()
                                    .url(entry.getKey())
                                    .get()
                                    .readString();

                            return wash.wash(content);
                        } catch (Exception e) {
                            log.error("爬取:{}, 失败", entry.getKey(), e);
                            return null;
                        }
                    })
                    .doOnError(throwable -> log.error(throwable.getMessage(), throwable))
                    .flatMap(ipProxyPos -> {
                        if (ipProxyPos == null)
                            return null;

                        List<IpProxyPo> result = ipProxyPos
                                .stream()
                                .parallel()
                                .filter(proxy -> {
                                    long speed = ProxyUtil.checkProxy(proxy.getIp(), proxy.getPort());
                                    if (speed < 0)
                                        return false;
                                    proxy.setSpeed(speed);
                                    return true;
                                })
                                .collect(Collectors.toList());
                        return Flowable.fromIterable(result);
                    })
                    .sequential()
                    .subscribe(ipProxyPo -> proxyService.saveOrUpdate(ipProxyPo));
        } catch (Exception e) {
            log.error("crawl error", e);
            crawling = false;
        }
    }
}
