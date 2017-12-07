package io.github.anotherme17.spiderproxy.service.proxy;

import io.github.anotherme17.spiderproxy.dao.proxy.ProxyPoolDao;
import io.github.anotherme17.spiderproxy.po.proxy.IpProxyPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lirenhao
 * date: 2017/12/4 上午11:49
 */
@Service
public class ProxyService {

    @Autowired
    private ProxyPoolDao dao;

    public void saveOrUpdate(IpProxyPo po) {
        IpProxyPo old = dao.getById(po.getId());

        if (old == null)
            dao.insertIpProxy(po);
        else
            dao.update(po);
    }
}
