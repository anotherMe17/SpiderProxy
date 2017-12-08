package io.github.anotherme17.spiderproxy.controller;

import io.github.anotherme17.base.utils.MD5Util;
import io.github.anotherme17.spiderproxy.po.proxy.IpProxyPo;
import io.github.anotherme17.spiderproxy.service.crawl.CrawlIpProxyService;
import io.github.anotherme17.spiderproxy.service.proxy.ProxyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lirenhao
 * date: 2017/12/4 下午6:28
 */
@Slf4j
@RestController
@RequestMapping(value = "/proxy")
public class ProxyController {

    @Autowired
    private CrawlIpProxyService crawlIpProxyService;

    @Autowired
    private ProxyService proxyService;

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public List<IpProxyPo> getAllProxy(HttpServletRequest request, HttpServletResponse rsp) {
        String token = request.getHeader("Crawl-Token");
        if (StringUtils.isEmpty(token) || !MD5Util.getEncode("another").equals(token))
            return new ArrayList<>();

        return proxyService.getAll();
    }

    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public String updateProxy(HttpServletRequest request, HttpServletResponse rsp) {
        String token = request.getHeader("Crawl-Token");
        if (StringUtils.isEmpty(token) || !MD5Util.getEncode("another").equals(token))
            return "token error";

        crawlIpProxyService.updateIpProxyPool();

        return "ok";
    }
}
