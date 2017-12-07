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
    private Long speed;
    private Date gmtCreate;
    private Date gmtModify;
}
