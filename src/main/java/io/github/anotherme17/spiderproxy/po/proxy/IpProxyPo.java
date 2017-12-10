package io.github.anotherme17.spiderproxy.po.proxy;

import lombok.Data;

import java.util.Date;

/**
 * @author lirenhao
 * date: 2017/12/1 下午4:16
 */
@Data
public class IpProxyPo {

    private String id;
    private String ip;
    private String port;
    private String location;

    private String anonymity;
    private String proxyType;

    private Long speed;
    private Date gmtCreate;
    private Date gmtModify;

    @Override
    public int hashCode() {
        return getId() == null ? 1 : getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IpProxyPo) {
            if (getId() != null && getId().equals(((IpProxyPo) obj).getId()))
                return true;
        }
        return false;
    }
}
