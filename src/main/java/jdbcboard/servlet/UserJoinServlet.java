package jdbcboard.servlet;

import jdbcboard.dao.BoardDao;
import jdbcboard.dao.BoardDaoImpl;
import jdbcboard.dao.UserDao;
import jdbcboard.dao.UserDaoImpl;
import jdbcboard.dto.Board;
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
import java.io.IOException;

@WebServlet(name = "UserJoinServlet", urlPatterns = "/userjoin")
public class UserJoinServlet extends HttpServlet {
    public UserJoinServlet(){
        System.out.println("UserJoinServlet()");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher =
                req.getRequestDispatcher("/WEB-INF/views/userjoin.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String userName = req.getParameter("user-name");
        String nickname = req.getParameter("user-nickname");
        String passwd = req.getParameter("user-pw");
        String passwdCheck = req.getParameter("user-pw_");
        String email = req.getParameter("user-email");

        if(userName != null && userName.length() < 2){
            resp.sendRedirect("/join?error=nameError");
            return;
        }

        // 암호1과 암호2가 같으냐.

        PasswordEncoder passwordEncoder =
                PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // 암호화 하는 코드
        String encodePasswd = passwordEncoder.encode(passwd);

        User user = new User(userName, nickname, encodePasswd, email);
        UserService userService = UserServiceImpl.getInstance();

        userService.addUser(user);

        System.out.println(user);

        resp.sendRedirect("/login");
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