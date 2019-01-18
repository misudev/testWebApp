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
        String pageStr = req.getParameter("page");
        int page = 1;
        long boardCount = 0;
        int totalPage = 0;

        try{
            page = Integer.parseInt(pageStr);
        }catch(Exception ignore){}

        BoardService boardService = new BoardServiceImpl();
        boardCount = boardService.getBoardCount();
        totalPage = (int) boardCount/SIZE + ( boardCount%SIZE == 0 ? 0: 1);
        System.out.println(totalPage);
        List<Board> boards = boardService.getBoards(page);

        for(Board b : boards){
            int depth = b.getDepth();
            String title = b.getTitle();
            for(int i = 0; i < depth; i ++){
                title = "re: &nbsp;" + title;
            }
            b.setTitle(title);
        }

        req.setAttribute("boards", boards);
        req.setAttribute("total-page", totalPage);

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
