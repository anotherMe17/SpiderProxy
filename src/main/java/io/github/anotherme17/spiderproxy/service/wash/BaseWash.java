package io.github.anotherme17.spiderproxy.service.wash;

import io.github.anotherme17.base.utils.MD5Util;
import io.github.anotherme17.spiderproxy.po.proxy.IpProxyPo;

import java.util.List;

/**
 * @author lirenhao
 * date: 2017/12/1 下午9:23
 */
public abstract class BaseWash {

    public abstract List<IpProxyPo> wash(String content);

    protected String makeId(String ip, String port) {
        String id = ip + port;
        return MD5Util.getEncode(id);
    }
}
