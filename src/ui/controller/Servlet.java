package ui.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Servlet")
public class Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("command");
        if (command == null)
            command = "";

        String destination = "welkom.jsp";

        switch (command) {
            case "switchLanguage":
                destination = switchLanguage(request, response);
                break;
            case "somethingMore":
                destination = somethingMore(request);
                break;
            default:
                destination = goHome(request);
        }

        request.getRequestDispatcher(destination).forward(request, response);
    }

    private String somethingMore(HttpServletRequest request) {
        Cookie cookie = getCookieWithKey(request, "language");
        if (cookie != null && cookie.getValue().equals("EN")) {
            return "somethingMore.jsp";
        } else
            return "meerTekst.jsp";
    }

    private String goHome(HttpServletRequest request) {
        Cookie cookie = getCookieWithKey(request, "language");
        if (cookie != null && cookie.getValue().equals("EN"))
            return "welcome.jsp";
        else
            return "welkom.jsp";
    }

    private String switchLanguage(HttpServletRequest request, HttpServletResponse response) {
        String destination = "welcome.jsp";
        // lookup for cookie with key language
        Cookie cookie = getCookieWithKey(request, "language");

        Cookie c = null;

        // cookie is not set yet
        // default home page is NL
        if (cookie == null || cookie.getValue().equals("NL")) {
            c = new Cookie("language", "EN");
            destination = "welcome.jsp";
        } else if (cookie.getValue().equals("EN")) {
            c = new Cookie("language", "NL");
            destination = "welkom.jsp";
        }
        response.addCookie(c);
        return destination;
    }

    private Cookie getCookieWithKey(HttpServletRequest request, String key) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null)
            return null;
        for (Cookie cookie : cookies
        ) {
            if (cookie.getName().equals(key))
                return cookie;
        }
        return null;
    }
}
