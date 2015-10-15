package com.kassandra.rest;

import java.io.IOException;

import javax.inject.Singleton;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;

@Singleton
public class ResponseCorsFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
    }


    public void destroy() {
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
            FilterChain filterChain) throws IOException, ServletException {
        if (servletResponse instanceof HttpServletResponse) {
            HttpServletResponse alteredResponse = ((HttpServletResponse) servletResponse);
            addHeaders(alteredResponse);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private void addHeaders(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods",
                "*, Cache-Control, Pragma, Origin, Authorization, X-Requested-With, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers",
                "*, Content-Type, GET, OPTIONS, X-XSRF-TOKEN");
    }
}
