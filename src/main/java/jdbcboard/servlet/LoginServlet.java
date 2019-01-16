package jdbcboard.servlet;

import jdbcboard.dao.UserDao;
import jdbcboard.dao.UserDaoImpl;
import jdbcboard.dto.User;
import jdbcboard.service.UserService;
import jdbcboard.service.UserServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    public LoginServlet(){
        System.out.println("LoginServlet()");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher =
                req.getRequestDispatcher("/WEB-INF/views/login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String email = req.getParameter("user-email");
        String passwd = req.getParameter("user-pw");

        UserService userService = new UserServiceImpl();
        Long userId = userService.hasUser(email, passwd);

        HttpSession session = req.getSession();
        if(userId != -1){
            session.setAttribute("signedUser", userId);
        }
        resp.sendRedirect("/board");
    }

    @Override
    public void destroy() {
        System.out.println("----- destroy -----");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("----- init -----");
    }
}