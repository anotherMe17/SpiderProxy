package io.github.anotherme17.spiderproxy.dto.proxy;

import com.google.common.base.Converter;
import com.sun.jna.platform.win32.COM.util.Convert;
import io.github.anotherme17.spiderproxy.po.proxy.IpProxyPo;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author lirenhao
 * date: 2017/12/12 下午3:05
 */
@Data
public class ProxyOutputDTO {

    private String ip;
    private String port;
    private String location;

    private String anonymity;
    private String proxyType;

    private Long speed;
    private Date gmtCreate;
    private Date gmtModify;

    public ProxyOutputDTO convertFor(IpProxyPo po) {
        ProxyOutputDTOConvert proxyOutputDTOConvert = new ProxyOutputDTOConvert();
        return proxyOutputDTOConvert.doBackward(po);
    }


    private static class ProxyOutputDTOConvert extends Converter<ProxyOutputDTO, IpProxyPo> {

        @Override
        protected IpProxyPo doForward(ProxyOutputDTO proxyOutputDTO) {
            throw new AssertionError("不支持正向转化方法!");
        }

        @Override
        protected ProxyOutputDTO doBackward(IpProxyPo po) {
            ProxyOutputDTO proxyOutputDTO = new ProxyOutputDTO();
            BeanUtils.copyProperties(po, proxyOutputDTO);
            return proxyOutputDTO;
        }
    }
}
