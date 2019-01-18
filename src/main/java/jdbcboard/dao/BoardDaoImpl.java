package jdbcboard.dao;

import jdbcboard.dto.Board;
import jdbcboard.util.ConnectionContextHolder;
import jdbcboard.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BoardDaoImpl implements BoardDao{
    @Override
    public Board getBoard(long idParam) {
        Board board = null; // return할 타입을 선언한다.

        Connection conn = null;
        try {
            conn = ConnectionContextHolder.getConnection();

            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.SELECT_BY_ID);) {
                ps.setLong(1, idParam);

                try(ResultSet rs = ps.executeQuery();) {
                    if (rs.next()) {
                        long id = rs.getLong(1);
                        long userId = rs.getLong(2);
                        String title = rs.getString(3);
                        String content = rs.getString(4);
                        String name = rs.getString(5);
                        Date regdate = rs.getDate(6);
                        int readCount = rs.getInt(7);
                        int depth = rs.getInt(8);
                        long thread = rs.getLong(9);

                        board = new Board(id, userId, name, title, content, regdate, readCount);
                        board.setDepth(depth);
                        board.setThread(thread);
                    }
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return board;
    }


    @Override
    public List<Board> getBoards(long start, int limit) {
        List<Board> list = new ArrayList<>();
        Connection conn = null;

        try {
            // a. DB 연결 - Connection
            //    DB연결을 하려면 필요한 정보가 있다. Driver classname, DB URL, DB UserId , DB User Password
            conn = ConnectionContextHolder.getConnection();
            // b. SELECT SQL 준비 - Connection
            //String sql = "SELECT b.id, b.user_id, b.title, b.content, u.name, b.regdate, b.read_count FROM board b INNER JOIN user u ON b.user_id = u.id ORDER BY b.thread DESC LIMIT ?, ?";
            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.SELECT_BY_PAGING);) {
                // c. 바인딩 - PreparedStatement
                ps.setLong(1, start); // 첫번째 물음표에 5를 바인딩한다.
                ps.setInt(2, limit);

                // d. SQL 실행 - PreparedStatement
                try (ResultSet rs = ps.executeQuery();) { // SELECT 문장을 실행, executeUpdate() - insert, update, delete

                    //id, user_id, title, nickname, regdate, read_count , depth, thread
                    while (rs.next()) {
                        long id = rs.getLong(1);
                        long userId = rs.getLong(2);
                        String title = rs.getString(3);
                        String name = rs.getString(4);
                        Date regdate = rs.getDate(5);
                        int readCount = rs.getInt(6);
                        int depth = rs.getInt(7);
                        long thread = rs.getLong(8);

                        Board board = new Board(id, userId, name, title, regdate, readCount, depth);
                        board.setThread(thread);
                        list.add(board);
                    }
                }

            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return list;
    }

    @Override
    public Long getCount() {
        long count = 0L;
        Connection conn = null;
        try {
            conn = ConnectionContextHolder.getConnection();

            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.COUNT_BOARD);) {
                try(ResultSet rs = ps.executeQuery();) {
                    if (rs.next()) {
                        count = rs.getLong(1);
                    }
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return count;
    }

    @Override
    public void updateReadCount(Long id) {
        Connection conn = null;

        try{
            conn = ConnectionContextHolder.getConnection();
            String sql = BoardDaoSQL.UPDATE_READCOUNT;
            try(PreparedStatement ps = conn.prepareStatement(sql);) {

                ps.setLong(1, id);

                ps.executeUpdate();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void addBoard(Board board) {
        Connection conn = null;

        try{
            conn = ConnectionContextHolder.getConnection();
            //String sql = "INSERT INTO board (thread, user_id, title, content) VALUES ((SELECT IFNULL(MAX(thread) + 100, 100) FROM board b),? , ?, ?)";
            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.INSERT_NEW);) {

                ps.setLong(1, board.getUserId());
                ps.setString(2, board.getNickName());
                ps.setString(3, board.getTitle());
                ps.setString(4, board.getContent());

                ps.executeUpdate();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void deleteBoard(long id) {
        Connection conn = null;

        try{
            conn = ConnectionContextHolder.getConnection();

            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.DELETE);) {
                //String sql = "DELETE FROM board WHERE id = ?";
                ps.setLong(1, id);
                ps.executeUpdate(); // 입력,수정,삭제 건수 가 리턴된다.
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void updateBoard(Board board) {
        Connection conn = null;
        try{
            conn = ConnectionContextHolder.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.UPDATE);) {
                ps.setString(1, board.getTitle());
                ps.setString(2, board.getContent());
                ps.setLong(3, board.getId());
                ps.executeUpdate();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public Board getThreadDepth(long id) {
        Board board = null; // return할 타입을 선언한다.
        Connection conn = null;
        try {
            conn = ConnectionContextHolder.getConnection();

            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.SELECT_THREAD_BY_ID);) {
                ps.setLong(1, id);

                try(ResultSet rs = ps.executeQuery();) {
                    if (rs.next()) {
                        long thread = rs.getLong(1);
                        int depth = rs.getInt(2);

                        board = new Board();
                        board.setThread(thread);
                        board.setDepth(depth);
                    }
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return board;
    }

    @Override
    public void addReply(Board board) {
        Connection conn = null;

        try{
            conn = ConnectionContextHolder.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.INSERT_REPLY);) {
                //thread, depth, user_id, nickname, title, content
                ps.setLong(1, board.getThread());
                ps.setInt(2, board.getDepth());
                ps.setLong(3, board.getUserId());
                ps.setString(4, board.getNickName());
                ps.setString(5, board.getTitle());
                ps.setString(6, board.getContent());

                ps.executeUpdate();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void updateThreadMinus(long thread) {
        Connection conn = null;
        try{
            conn = ConnectionContextHolder.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.UPDATE_THREAD_MINUS);) {
                ps.setLong(1, (thread/100) * 100);  // min
                ps.setLong(2, thread );    //max
                ps.executeUpdate();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void updateThreadPlus(long min, long max) {

    }

    @Override
    public void updateMaxThread() {
        Connection conn = null;
        try{
            conn = ConnectionContextHolder.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.UPDATE_MAX_THREAD);) {
                ps.executeUpdate();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public void updateMaxThreadMinus() {
        Connection conn = null;
        try{
            conn = ConnectionContextHolder.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.UPDATE_MAX_THREAD_MINUS);) {
                ps.executeUpdate();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void updateCountBoard() {
        Connection conn = null;
        try{
            conn = ConnectionContextHolder.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.UPDATE_COUNT_BOARD);) {
                ps.executeUpdate();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }

    @Override
    public void updateCountBoardMinus() {
        Connection conn = null;
        try{
            conn = ConnectionContextHolder.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.UPDATE_COUNT_BOARD_MINUS);) {
                ps.executeUpdate();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }

    }


    @Override
    public boolean existReply(long thread) {
        boolean flag = false;
        Connection conn = null;
        try{
            conn = ConnectionContextHolder.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.EXIST_BY_THREAD);) {
                ps.setLong(1, thread - 1);
                try (ResultSet rs = ps.executeQuery();) {
                    if (rs.next()) {
                        int result = rs.getInt(1);
                        flag = result == 1 ? true : false;
                    }
                }
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return flag;
    }

    @Override
    public long getMaxThread() {
        long thread = 0;
        Connection conn = null;
        try {
            conn = ConnectionContextHolder.getConnection();

            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.SELECT_MAX_THREAD);) {
                try(ResultSet rs = ps.executeQuery();) {
                    if (rs.next()) {
                        thread = rs.getLong(1);
                    }
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return thread;
    }

    @Override
    public long getCountBoard() {
        long count = 0;
        Connection conn = null;
        try {
            conn = ConnectionContextHolder.getConnection();

            try(PreparedStatement ps = conn.prepareStatement(BoardDaoSQL.SELECT_COUNT_BOARD);) {
                try(ResultSet rs = ps.executeQuery();) {
                    if (rs.next()) {
                        count = rs.getLong(1);
                    }
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return count;
    }
}

