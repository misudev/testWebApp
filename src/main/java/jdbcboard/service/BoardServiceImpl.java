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

        int start = SIZE * page - SIZE;
        int limit = SIZE;

        try (Connection conn = DBUtil.getInstance().getConnection();){
            ConnectionContextHolder.setConnection(conn);
            boards = boardDao.getBoards(start, limit);
        }catch(Exception ex){
            ex.printStackTrace();
        }

        return boards;
    }

    public long getBoardCount() {
        long count = 0L;
        BoardDao boardDao = new BoardDaoImpl();
        Connection conn = null;
        try{
            conn = DBUtil.getInstance().getConnection();
            ConnectionContextHolder.setConnection(conn);
            count = boardDao.getCount();
        }catch(Exception ex){
            DBUtil.rollback(conn);
            ex.printStackTrace();
        }finally {
            DBUtil.close(conn);
        }
        return count;
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
    public void deleteBoard(Long id, Long sigendId) {
        BoardDao boardDao= new BoardDaoImpl();
        Connection conn = null;
        try{
            conn = DBUtil.getInstance().getConnection();
            ConnectionContextHolder.setConnection(conn);

            Board board = boardDao.getBoard(id);
            if(board.getUserId()==sigendId){
                if(!boardDao.existReply(board.getThread())){
                    boardDao.deleteBoard(id);
                    boardDao.updateCountBoardMinus();
                    if(board.getThread() == boardDao.getMaxThread()*100){
                        boardDao.updateMaxThreadMinus();
                    }
                }
            }

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
            // board 추가.
            boardDao.addBoard(board);
            // MAX THREAD + 1 , COUNT BOARD + 1
            boardDao.updateMaxThread();
            boardDao.updateCountBoard();
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

    @Override
    public void addReply(long parentId, Board board) {
        BoardDao boardDao= new BoardDaoImpl();
        Connection conn = null;
        try{
            conn = DBUtil.getInstance().getConnection();
            ConnectionContextHolder.setConnection(conn);
            // 1. 부모 thread, depth 를 가져온다.
            Board tmpBoard = boardDao.getThreadDepth(parentId);
            Long thread = tmpBoard.getThread();
            int depth = tmpBoard.getDepth();
            // 2. thread 업데이트. ( thread unique??...)
            boardDao.updateThreadMinus(thread);
            // 3. board 설정
            board.setThread(thread - 1);
            board.setDepth(depth + 1);
            boardDao.addReply(board);
            // 4. manage 업데이트.
            boardDao.updateCountBoard();
            conn.commit(); // 트랜젝션 commit
        }catch (Exception ex){
            DBUtil.rollback(conn);
            ex.printStackTrace();
        }finally {
            DBUtil.close(conn);
        }
    }
}
