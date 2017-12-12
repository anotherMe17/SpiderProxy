package io.github.anotherme17.spiderproxy.controller;

import io.github.anotherme17.spiderproxy.dto.HttpResult;
import io.github.anotherme17.spiderproxy.dto.proxy.ProxyOutputDTO;
import io.github.anotherme17.spiderproxy.exception.ProxyException;
import io.github.anotherme17.spiderproxy.service.proxy.ProxyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private ProxyService proxyService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public HttpResult<List<ProxyOutputDTO>> getProxy(
            @RequestParam(value = "proxyType",required = false) String proxyType,
            @RequestParam(value = "anonymity",required = false) String anonymity,
            @RequestParam(value = "offset",required = false) Integer offset) throws ProxyException {

        return HttpResult.<List<ProxyOutputDTO>>of()
                .setCode(0).setMsg("ok")
                .setDate(proxyService.getByOffset(proxyType, anonymity, offset));
    }
}
