package com.chernenkov.webservlet.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.chernenkov.webservlet.command.RequestAttribute.USER_NAME;
import static com.chernenkov.webservlet.command.RequestAttribute.USER_TYPE;

@WebFilter(urlPatterns = {"/pages/main.jsp"})

public class MainPageFilter implements Filter {
    static Logger logger = LogManager.getLogger();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        logger.debug("Filter is working");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        session.getAttribute(USER_TYPE);
        httpServletRequest.getRequestDispatcher("index.jsp").forward(request, response);
        chain.doFilter(request, response);
    }
}