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

@WebServlet(name = "UpdateServlet", urlPatterns = "/update")
public class UpdateFormServlet extends HttpServlet {
    public UpdateFormServlet(){
        System.out.println("UpdateServlet()");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        BoardService boardService = new BoardServiceImpl();
        Board board = boardService.getBoard(Long.parseLong(req.getParameter("id")));

        if(board == null){
            // 오류 화면으로 redirect
            return;
        }
        req.setAttribute("board", board);
        User user = (User)session.getAttribute("logininfo");
        if(user.getId() != board.getUserId()){
            resp.sendRedirect("/board");
        }else {
            RequestDispatcher requestDispatcher =
                    req.getRequestDispatcher("/WEB-INF/views/updateform.jsp");
            requestDispatcher.forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        HttpSession session = req.getSession();
        User user = (User)session.getAttribute("logininfo");

        Long id = Long.parseLong(req.getParameter("id"));
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        Board board = new Board(user.getId(), user.getNickname(),title,content);
        board.setId(id);


        BoardService boardService = new BoardServiceImpl();
        boardService.updateBoard(board);
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
