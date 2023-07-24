package com.chernenkov.webservlet.controller.filter;

import com.chernenkov.webservlet.entity.UserType;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

import static com.chernenkov.webservlet.command.PageName.INDEX_PAGE;
import static com.chernenkov.webservlet.command.RequestAttribute.USER_TYPE;

@WebFilter (urlPatterns = {"/*"})
public class RoleFilter implements Filter {
    static Logger logger = LogManager.getLogger();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        UserType type = (UserType) session.getAttribute(USER_TYPE);
        if (type == null){
            logger.info ("Role filter sending to index page");
            type = UserType.GUEST;
            session.setAttribute(USER_TYPE, type);
            RequestDispatcher dispatcher = servletRequest.getServletContext().getRequestDispatcher(INDEX_PAGE);
            dispatcher.forward(req,resp);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
