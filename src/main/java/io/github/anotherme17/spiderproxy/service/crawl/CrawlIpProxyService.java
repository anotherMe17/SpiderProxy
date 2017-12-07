package io.github.anotherme17.spiderproxy.service.crawl;

import io.github.anotherme17.base.http.ProxyUtil;
import io.github.anotherme17.okhttp3util.OkClient;
import io.github.anotherme17.okhttp3util.interceptor.HttpLoggingInterceptor;
import io.github.anotherme17.okhttp3util.util.DeviceInfoUtil;
import io.github.anotherme17.spiderproxy.common.CrawlConstants;
import io.github.anotherme17.spiderproxy.po.proxy.IpProxyPo;
import io.github.anotherme17.spiderproxy.service.SpringBeanService;
import io.github.anotherme17.spiderproxy.service.proxy.ProxyService;
import io.github.anotherme17.spiderproxy.service.wash.BaseWash;
import io.reactivex.Flowable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lirenhao
 * date: 2017/12/4 上午10:59
 */
@Slf4j
@Service
public class CrawlIpProxyService {

    @Autowired
    private ProxyService proxyService;

    public void updateIpProxyPool() {

        OkClient client = new OkClient(HttpLoggingInterceptor.Level.BODY, null, DeviceInfoUtil.randomUserAgent());

        Flowable.fromIterable(CrawlConstants.CRAWL_MAP.entrySet())
                .parallel()
                .map(entry -> {
                    BaseWash wash = SpringBeanService.getBean(entry.getValue(), BaseWash.class);
                    if (wash == null)
                        return null;

                    String content = client.builder()
                            .url(entry.getKey())
                            .headers(getHeads())
                            .get()
                            .readString();

                    return wash.wash(content);
                })
                .doOnError(throwable -> log.error(throwable.getMessage(),throwable))
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
    }

    private Map<String,String> getHeads(){
        return new HashMap<String,String>(){
            {
                put("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
                put("Accept-Encoding","gzip, deflate");
                put("Accept-Language","zh-CN,zh;q=0.9,en;q=0.8");
                put("Connection","keep-alive");
            }
        };
    }
}
