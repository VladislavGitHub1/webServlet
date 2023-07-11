package com.chernenkov.webservlet.controller;

import com.chernenkov.webservlet.command.Command;
import com.chernenkov.webservlet.command.CommandType;
import com.chernenkov.webservlet.exception.CommandException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import static com.chernenkov.webservlet.util.RequestParameter.COMMAND;

@WebServlet(name = "helloServlet", urlPatterns = "/controller")
public class Controller extends HttpServlet {
    @Override
    public void init() {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String commandStr = request.getParameter(COMMAND);
        Command command = CommandType.define(commandStr);
        String page = null;
        try {
            page = command.execute(request);
        } catch (CommandException e) {
           e.printStackTrace(); //TODO
        }
        request.getRequestDispatcher(page).forward(request, response);
    }
}
