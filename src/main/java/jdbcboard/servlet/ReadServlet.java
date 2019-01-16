package jdbcboard.servlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import jdbcboard.dao.BoardDao;
import jdbcboard.dao.BoardDaoImpl;
import jdbcboard.dto.Board;
import jdbcboard.service.BoardService;
import jdbcboard.service.BoardServiceImpl;

@WebServlet(name = "ReadServlet", urlPatterns = "/read")
public class ReadServlet extends HttpServlet {
    public ReadServlet(){
        System.out.println("ReadServlet()");
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        Long id = 0L;
        try{
            id = Long.parseLong(idStr);
        }catch(Exception ex){
            // 오류 화면으로 redirect
            return;
        }
//        BoardDao boardDao = new BoardDaoImpl();
//        boardDao.updateReadCount(id);
//        Board board = boardDao.getBoard(id);
        BoardService boardService = new BoardServiceImpl();
        Board board = boardService.getBoard(id);

        if(board == null){
            // 오류 화면으로 redirect
            return;
        }

        req.setAttribute("board", board);

        long signedId = -1;
        HttpSession session = req.getSession();
        if (session.getAttribute("signedUser") != null) {
            signedId = (long) session.getAttribute("signedUser");
        }
        req.setAttribute("signedId", signedId);

        RequestDispatcher requestDispatcher =
                req.getRequestDispatcher("/WEB-INF/views/read.jsp");
        requestDispatcher.forward(req, resp);

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
