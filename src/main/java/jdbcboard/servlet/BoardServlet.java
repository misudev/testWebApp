package jdbcboard.servlet;

import jdbcboard.dao.BoardDao;
import jdbcboard.dao.BoardDaoImpl;
import jdbcboard.dto.Board;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "BoardServlet", urlPatterns = "/board")
public class BoardServlet extends HttpServlet {
    private static final int SIZE = 5;

    public BoardServlet(){
        System.out.println("HelloServlet()");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. page 값을 파라미터로 읽어들인다. 값이 없으면 기본값은 1페이지로 한다.
        String pageStr = req.getParameter("page");
        int page = 1;
        long signedId = -1;
        try{
            page = Integer.parseInt(pageStr);
        }catch(Exception ignore){}

        BoardService boardService = new BoardServiceImpl();
        List<Board> boards = boardService.getBoards(page);

        // 3. request에 2에서 구한값을 setAttribute로 담아서 jsp에게 전달한다.
        req.setAttribute("boards", boards);
        // 5. jsp에서는 jstl과 el로 결과를 출력한다.

        // + 로그인시 세션에서 로그인 user id 를 가져오고 이를 board.jsp에 보내준다.
//        HttpSession session = req.getSession();
//        if (session.getAttribute("signedUser") != null) {
//            signedId = (long) session.getAttribute("signedUser");
//        }
//        req.setAttribute("signedId", signedId);
        RequestDispatcher requestDispatcher =
                req.getRequestDispatcher("/WEB-INF/views/board.jsp");
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
