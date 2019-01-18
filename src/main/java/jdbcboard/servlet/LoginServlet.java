package jdbcboard.servlet;

import jdbcboard.dao.UserDao;
import jdbcboard.dao.UserDaoImpl;
import jdbcboard.dto.User;
import jdbcboard.service.UserService;
import jdbcboard.service.UserServiceImpl;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

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

        UserService userService = UserServiceImpl.getInstance();
        String encodePasswd = userService.getPasswdByEmail(email);
        if(encodePasswd != null){
            PasswordEncoder passwordEncoder =
                    PasswordEncoderFactories.createDelegatingPasswordEncoder();
            boolean matches = passwordEncoder.matches(passwd, encodePasswd);
            if(matches){
                // 로그인정보를 세션에 저장.
                HttpSession session = req.getSession();
                User loginedUser = userService.getUserByEmail(email);
                System.out.println("logined User : " + loginedUser.getNickname());
                session.setAttribute("logininfo",loginedUser );
                System.out.println("암호가 맞아요.");
            }else{
                // 암호가 틀렸어요.
                System.out.println("암호가 틀렸어요.");
            }
        }
        String redirect = req.getParameter("redirect");
        System.out.println(redirect);
        if(redirect!= ""){
            resp.sendRedirect("/"+redirect);
            return;
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