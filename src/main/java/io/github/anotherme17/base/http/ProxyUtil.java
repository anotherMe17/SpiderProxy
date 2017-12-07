package io.github.anotherme17.base.http;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author lirenhao
 * date: 2017/12/4 上午11:33
 */
public class ProxyUtil {

    public static long checkProxy(String hostName, String port) {
        if (StringUtils.isEmpty(hostName) || StringUtils.isEmpty(port))
            return -1;

        long startTime = System.currentTimeMillis();

        Socket socket = null;
        try {
            socket = new Socket();
            InetSocketAddress endpointSocketAddr = new InetSocketAddress(hostName, Integer.parseInt(port));
            socket.connect(endpointSocketAddr, 3000);
            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        } catch (IOException e) {
            return -1;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
//                    logger.warn("Error occurred while closing socket of validating proxy", e);
                }
            }
        }
    }

}
