package jdbcboard.service;


import jdbcboard.dao.BoardDao;
import jdbcboard.dao.BoardDaoImpl;
import jdbcboard.dto.Board;
import jdbcboard.util.ConnectionContextHolder;
import jdbcboard.util.DBUtil;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class BoardServiceImpl implements BoardService{
    public static final int SIZE = 5;
    @Override
    public List<Board> getBoards(int page) {
        List<Board> boards = new ArrayList<>();
        BoardDao boardDao = new BoardDaoImpl();
        Connection conn = null;

        int start = SIZE * page - SIZE;
        int limit = SIZE;

        try {
            conn = DBUtil.getInstance().getConnection();
            ConnectionContextHolder.setConnection(conn);
            boards = boardDao.getBoards(start, limit);
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            DBUtil.close(conn);
        }

        return boards;
    }

    @Override
    public Board getBoard(Long id) {
        Board board = null;
        BoardDao boardDao = new BoardDaoImpl();
        Connection conn = null;
        try{
            conn = DBUtil.getInstance().getConnection();
            ConnectionContextHolder.setConnection(conn);
            board = boardDao.getBoard(id);
            boardDao.updateReadCount(id);
            conn.commit(); // 트랜젝션 commit
        }catch(Exception ex){
            DBUtil.rollback(conn);
            ex.printStackTrace();
        }finally {
            DBUtil.close(conn);
        }

        return board;
    }

    @Override
    public void deleteBoard(Long id) {
        BoardDao boardDao= new BoardDaoImpl();
        Connection conn = null;
        try{
            conn = DBUtil.getInstance().getConnection();
            ConnectionContextHolder.setConnection(conn);
            boardDao.deleteBoard(id);
            conn.commit(); // 트랜젝션 commit
        }catch (Exception ex){
            DBUtil.rollback(conn);
            ex.printStackTrace();
        }finally {
            DBUtil.close(conn);
        }
    }

    @Override
    public void addBoard(Board board) {
        BoardDao boardDao= new BoardDaoImpl();
        Connection conn = null;
        try{
            conn = DBUtil.getInstance().getConnection();
            ConnectionContextHolder.setConnection(conn);
            boardDao.addBoard(board);
            conn.commit(); // 트랜젝션 commit
        }catch (Exception ex){
            DBUtil.rollback(conn);
            ex.printStackTrace();
        }finally {
            DBUtil.close(conn);
        }
    }

    @Override
    public void updateBoard(Board board) {
        BoardDao boardDao= new BoardDaoImpl();
        Connection conn = null;
        try{
            conn = DBUtil.getInstance().getConnection();
            ConnectionContextHolder.setConnection(conn);
            boardDao.updateBoard(board);
            conn.commit(); // 트랜젝션 commit
        }catch (Exception ex){
            DBUtil.rollback(conn);
            ex.printStackTrace();
        }finally {
            DBUtil.close(conn);
        }
    }
}
