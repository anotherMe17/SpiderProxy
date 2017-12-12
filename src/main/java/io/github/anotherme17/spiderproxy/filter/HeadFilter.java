package io.github.anotherme17.spiderproxy.filter;

import io.github.anotherme17.base.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author lirenhao
 * date: 2017/12/11 下午8:12
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "headFilter")
public class HeadFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String token = httpRequest.getHeader("Token");

        if (StringUtils.isEmpty(token) || !MD5Util.getEncode("another").equals(token))
            return;

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
