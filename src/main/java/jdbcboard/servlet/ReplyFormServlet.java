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


@WebServlet(name = "ReplyServlet", urlPatterns = "/reply")
public class ReplyFormServlet extends HttpServlet {
    public ReplyFormServlet(){
        System.out.println("ReplyServlet()");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        long id = 0;
        String idStr = req.getParameter("id");

        try{
            id = Long.parseLong(idStr);
        }catch(Exception ex){
            ex.printStackTrace();
            return;
        }

        BoardService boardService = new BoardServiceImpl();
        Board board = boardService.getBoard(id);

        String content = board.getContent();
        content = content.replaceAll("\n", "\n> ");
        System.out.println(content);
        board.setContent(content);

        req.setAttribute("board", board);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/views/replyform.jsp");
        requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        HttpSession session = req.getSession();
        long id = 0;
        String idStr = req.getParameter("parent-id");

        try{
            id = Long.parseLong(idStr);
        }catch(Exception ex){
            ex.printStackTrace();
            return;
        }

        String title = req.getParameter("title");
        String content = req.getParameter("content");

        User user = (User) req.getSession().getAttribute("logininfo");


        Board board = new Board(user.getId(), user.getNickname(), title, content);

        BoardService boardService = new BoardServiceImpl();
        boardService.addReply(id, board);
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
