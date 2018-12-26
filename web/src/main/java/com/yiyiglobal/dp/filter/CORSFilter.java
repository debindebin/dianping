package com.yiyiglobal.dp.filter;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter",urlPatterns = "/*")
public class CORSFilter implements javax.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, javax.servlet.FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse=(HttpServletResponse)servletResponse;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authorization, Authentication, Origin, X-Requested-With, Content-Type, Accept");
        filterChain.doFilter(servletRequest,httpServletResponse);
    }

    @Override
    public void destroy() {

    }

}
