package io.github.anotherme17.spiderproxy.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lirenhao
 * date: 2017/12/4 上午11:02
 */
public class CrawlConstants {

    // TODO: 2017/12/11  更多来源网站
    public static final Map<String, String> CRAWL_MAP = new HashMap<String, String>() {
        {
//            put("http://cn-proxy.com/china.ico", "cnProxyWashService");
            put("http://www.xicidaili.com/", "xiciProxyWashService");
        }
    };
}
