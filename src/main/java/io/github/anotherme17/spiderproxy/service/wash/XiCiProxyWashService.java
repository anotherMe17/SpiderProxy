package io.github.anotherme17.spiderproxy.service.wash;

import io.github.anotherme17.spiderproxy.po.proxy.IpProxyPo;
import io.github.anotherme17.spiderproxy.service.wash.base.BaseWash;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author lirenhao
 * date: 2017/12/8 上午10:21
 */
@Service("xiciProxyWashService")
public class XiCiProxyWashService extends BaseWash {


    @Override
    public List<IpProxyPo> wash(String content) {
        List<IpProxyPo> pos = new ArrayList<>();

        if (StringUtils.isEmpty(content))
            return pos;

        Document document = Jsoup.parse(content);
        Elements elements = document.select("table[id=ip_list]");
        Elements trs = elements.get(0).getElementsByTag("tr");
        Elements td;
        IpProxyPo po;
        Date nowDate = new Date();
        for (Element tr : trs) {
            td = tr.getElementsByTag("td");
            if (td.size() < 8)
                continue;

            po = new IpProxyPo();

            po.setIp(td.get(1).text());
            po.setPort(td.get(2).text());
            po.setLocation(td.get(3).text());

            po.setGmtCreate(nowDate);
            po.setGmtModify(nowDate);

            po.setId(makeId(po.getIp(), po.getPort()));

            pos.add(po);
        }


        return pos;
    }
}
