package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.AccountService;


/**
 *
 * @author marce
 */
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        User newUser = (User) session.getAttribute("user");

        String logout = request.getParameter("logout");

        if (logout != null) {
            session.invalidate();
            request.setAttribute("message", "You have successfully logged out.");
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(request, response);
        }
        else if (newUser != null) {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(request, response);
            response.sendRedirect("home");
        }
        else {
            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
                .forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        String userInput = request.getParameter("username");
        String passInput = request.getParameter("password");
        
        User newUser = new User(userInput, passInput);
        AccountService aService = new AccountService();

        if (aService.login(userInput, passInput) == null) {

            request.setAttribute("username", userInput);
            request.setAttribute("password", passInput);
            session.setAttribute("user", userInput);
            request.setAttribute("message", "Please provide a username or password.");

            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
                    .forward(request, response);
        }

        if (newUser == null) {
            request.setAttribute("message", "Invalid username or password.");

            getServletContext().getRequestDispatcher("/WEB-INF/login.jsp")
                    .forward(request, response);
        }
        else {
            request.setAttribute("username", newUser);
            session.setAttribute("user", userInput);
            response.sendRedirect("home");
        }
    }
}