package io.github.anotherme17.spiderproxy.service.proxy;

import io.github.anotherme17.spiderproxy.dao.proxy.ProxyPoolDao;
import io.github.anotherme17.spiderproxy.po.proxy.IpProxyPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lirenhao
 * date: 2017/12/4 上午11:49
 */
@Service
public class ProxyService {

    @Autowired
    private ProxyPoolDao dao;

    public void update(IpProxyPo po) {
        dao.update(po);
    }

    public void saveOrUpdate(IpProxyPo po) {
        IpProxyPo old = dao.getById(po.getId());

        if (old == null)
            dao.insertIpProxy(po);
        else
            dao.update(po);
    }

    public List<IpProxyPo> getAll() {
        return dao.getAll();
    }

    public void deleteById(String id) {
        dao.deleteById(id);
    }
}
