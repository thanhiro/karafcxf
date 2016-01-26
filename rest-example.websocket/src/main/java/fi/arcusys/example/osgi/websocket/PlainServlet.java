package fi.arcusys.example.osgi.websocket;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class PlainServlet extends HttpServlet {
    public void init() throws ServletException {
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws ServletException, IOException {
        // Set response content type
        response.setContentType("text/html");

        // Actual logic goes here.
        PrintWriter out = response.getWriter();
        out.println("plain am I");
    }

    public void destroy() {
        // do nothing.
    }
}