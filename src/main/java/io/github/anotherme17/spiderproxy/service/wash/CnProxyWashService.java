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
 * date: 2017/12/1 下午9:24
 */
@Service("cnProxyWashService")
public class CnProxyWashService extends BaseWash {


    @Override
    public List<IpProxyPo> wash(String content) {
        List<IpProxyPo> pos = new ArrayList<>();

        if (StringUtils.isEmpty(content))
            return pos;

        Document document = Jsoup.parse(content);
        Elements elements = document.select("table[class=sortable]");
        Elements tr;
        for (Element element : elements) {
            tr = element.getElementsByTag("tr");
            if (tr.size() <= 2)
                return pos;

            Elements td;
            IpProxyPo po;
            Date nowDate = new Date();
            for (int i = 2; i < tr.size(); i++) {
                td = tr.get(i).getElementsByTag("td");

                po = new IpProxyPo();

                po.setIp(td.get(0).text());
                po.setPort(td.get(1).text());
                po.setLocation(td.get(2).text());

                po.setGmtCreate(nowDate);
                po.setGmtModify(nowDate);

                po.setId(makeId(po.getIp(), po.getPort()));

                pos.add(po);
            }
        }

        return pos;
    }
}
