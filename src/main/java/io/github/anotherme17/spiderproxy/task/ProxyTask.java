package io.github.anotherme17.spiderproxy.task;

import io.github.anotherme17.spiderproxy.service.crawl.CrawlIpProxyService;
import io.github.anotherme17.spiderproxy.task.base.BaseTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

/**
 * @author lirenhao
 * date: 2017/12/11 下午7:39
 */
@Slf4j
@Order(2)
@Service("proxyTask")
@Scope("prototype")
public class ProxyTask extends BaseTask {

    @Autowired
    private CrawlIpProxyService crawlIpProxyService;

    @Override
    public void run() {
        log.info("定时任务, 更新代理池");
        crawlIpProxyService.updateIpProxyPool();
    }
}
