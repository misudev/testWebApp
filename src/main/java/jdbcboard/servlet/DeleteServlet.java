package jdbcboard.servlet;

import jdbcboard.dao.BoardDao;
import jdbcboard.dao.BoardDaoImpl;
import jdbcboard.dto.Board;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DeleteServlet", urlPatterns = "/delete")
public class DeleteServlet extends HttpServlet {
    public DeleteServlet(){
        System.out.println("jdbcboard.servlet.ReadServlet()");
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 로그인한 관리자인가? 관리자일경우에만 실행.
        HttpSession session = req.getSession();

        long signedId = -1;
        if (session.getAttribute("signedUser") != null) {
            signedId = (long) session.getAttribute("signedUser");
        }else {
            return;
        }

        Long id = 0L;
        try{
            String idStr = req.getParameter("id");
            id = Long.parseLong(idStr);
        }catch(Exception ex){
            // id가 잘못되었을 경우엔 에러페이지로 이동.
        }
        BoardDao boardDao = new BoardDaoImpl();
        Board board = boardDao.getBoard(id);
        if(board.getUserId() == signedId){
            boardDao.deleteBoard(id);
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
