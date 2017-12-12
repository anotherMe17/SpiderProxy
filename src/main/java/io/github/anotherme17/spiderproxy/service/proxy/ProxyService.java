package io.github.anotherme17.spiderproxy.service.proxy;

import io.github.anotherme17.spiderproxy.dao.proxy.ProxyPoolDao;
import io.github.anotherme17.spiderproxy.dto.proxy.ProxyOutputDTO;
import io.github.anotherme17.spiderproxy.exception.ProxyException;
import io.github.anotherme17.spiderproxy.po.proxy.IpProxyPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lirenhao
 * date: 2017/12/4 上午11:49
 */
@Service
public class ProxyService {

    @Autowired
    private ProxyPoolDao proxyPoolDao;

    public List<ProxyOutputDTO> getByOffset(String proxyType, String anonymity, Integer offset) throws ProxyException {
        if (offset == null)
            offset = 0;

        List<IpProxyPo> pos = proxyPoolDao.getProxy(proxyType, anonymity, offset);

        if (pos.size() == 0)
            throw new ProxyException("empty proxy");

        List<ProxyOutputDTO> proxyOutputDTOS = pos
                .stream()
//                .filter(po -> {
//                    long speed = ProxyUtil.checkProxy(po.getIp(), po.getPort());
//                    if (speed < 0) {
//                        deleteById(po.getId());
//                        return false;
//                    } else {
//                        po.setSpeed(speed);
//                        po.setGmtModify(new Date());
//                        update(po);
//                        return true;
//                    }
//                })
                .map(po -> new ProxyOutputDTO().convertFor(po))
                .collect(Collectors.toList());
//
//        if (proxyOutputDTOS.size() < 1) {
//            proxyOutputDTOS.addAll(getByOffset(proxyType, anonymity, offset));
//        }

        return proxyOutputDTOS;
    }

    public void update(IpProxyPo po) {
        proxyPoolDao.update(po);
    }

    public void saveOrUpdate(IpProxyPo po) {
        IpProxyPo old = proxyPoolDao.getById(po.getId());

        if (old == null)
            proxyPoolDao.insertIpProxy(po);
        else
            proxyPoolDao.update(po);
    }

    public List<IpProxyPo> getAll() {
        return proxyPoolDao.getAll();
    }

    public void deleteById(String id) {
        proxyPoolDao.deleteById(id);
    }
}
