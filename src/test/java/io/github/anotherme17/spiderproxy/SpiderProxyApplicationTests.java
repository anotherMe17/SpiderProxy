package io.github.anotherme17.spiderproxy;

import io.github.anotherme17.spiderproxy.dao.proxy.ProxyPoolDao;
import io.github.anotherme17.spiderproxy.po.proxy.IpProxyPo;
import io.github.anotherme17.spiderproxy.service.crawl.CrawlIpProxyService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderProxyApplicationTests {

    @Autowired
    ProxyPoolDao proxyPoolDao;

    @Autowired
    CrawlIpProxyService crawlIpProxyService;

    @Test
    @Transactional
    @Rollback(value = false)
    public void contextLoads() {
        IpProxyPo po = new IpProxyPo();
        po.setId("tttt");
        po.setIp("192.168.0.1");
        proxyPoolDao.insertIpProxy(po);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    public void crawl() {
        crawlIpProxyService.updateIpProxyPool();
    }


}
