package com.chernenkov.webservlet.command.impl;

import com.chernenkov.webservlet.command.Command;
import jakarta.servlet.http.HttpServletRequest;

public class LogoutCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        return null;
    }
}
