package io.github.anotherme17.spiderproxy.dao.proxy;

import io.github.anotherme17.spiderproxy.po.proxy.IpProxyPo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author lirenhao
 * date: 2017/12/1 下午4:07
 */
@Mapper
@Repository
public interface ProxyPoolDao {

    void insertIpProxy(IpProxyPo po);

    IpProxyPo getById(String id);

    void update(IpProxyPo po);
}
