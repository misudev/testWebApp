package jdbcboard.servlet;

import jdbcboard.dao.BoardDao;
import jdbcboard.dao.BoardDaoImpl;
import jdbcboard.dto.Board;
import jdbcboard.dto.User;
import jdbcboard.service.BoardService;
import jdbcboard.service.BoardServiceImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet(name = "WriteServlet", urlPatterns = "/write")
public class WriteFormServlet extends HttpServlet {
    public WriteFormServlet(){
        System.out.println("WriteServlet()");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            RequestDispatcher requestDispatcher =
                    req.getRequestDispatcher("/WEB-INF/views/writeform.jsp");
            requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        //Long userId = (Long)session.getAttribute("signedUser");
        User user = (User)session.getAttribute("logininfo");
        String title = req.getParameter("title");
        String content = req.getParameter("content");

        BoardService boardService = new BoardServiceImpl();
        Board board = new Board(user.getId(), user.getNickname(), title, content);
        boardService.addBoard(board);
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
