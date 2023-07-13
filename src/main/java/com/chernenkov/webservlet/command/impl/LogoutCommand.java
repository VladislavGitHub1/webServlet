package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import jakarta.servlet.http.HttpServletRequest;
import static com.chernenkov.webservlet.util.PageName.INDEX_PAGE;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        request.getSession().invalidate();
        return INDEX_PAGE;
    }
}
