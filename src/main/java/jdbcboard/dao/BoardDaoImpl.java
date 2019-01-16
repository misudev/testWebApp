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
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // a. DB 연결 - Connection
            //    DB연결을 하려면 필요한 정보가 있다. Driver classname, DB URL, DB UserId , DB User Password
            conn = ConnectionContextHolder.getConnection();

            // b. SELECT SQL 준비 - Connection
            String sql = "SELECT b.id, b.user_id, b.title, b.content, u.name, b.regdate, b.read_count FROM board b INNER JOIN user u ON b.user_id = u.id where b.id = ?";
            ps = conn.prepareStatement(sql);
            // c. 바인딩 - PreparedStatement
            ps.setLong(1, idParam); // 첫번째 물음표에 5를 바인딩한다.

            // d. SQL 실행 - PreparedStatement
            rs = ps.executeQuery(); // SELECT 문장을 실행, executeUpdate() - insert, update, delete

            // e. 1건의 row를 읽어온다. row는 여러개의 컬럼으로 구성되어 있다. - ResultSet
            // f. e에서 읽어오지 못하는 경우도 있다.
            if(rs.next()){
                long id = rs.getLong(1);
                long userId = rs.getLong(2);
                String title = rs.getString(3);
                String content = rs.getString(4);
                String name = rs.getString(5);
                Date regdate = rs.getDate(6);
                int readCount = rs.getInt(7);

                board = new Board(id, userId, name, title, content, regdate, readCount);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            // g. ResultSet, PreparedStatement, Connection 순으로 close를 한다.
            DBUtil.close(rs, ps);
        }

        return board;
    }


    @Override
    public List<Board> getBoards(long start, int limit) {
        List<Board> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // a. DB 연결 - Connection
            //    DB연결을 하려면 필요한 정보가 있다. Driver classname, DB URL, DB UserId , DB User Password
            conn = ConnectionContextHolder.getConnection();
            if(conn != null) {
                System.out.println("conn ok!");
                System.out.println(conn.getClass().getName());
            }
            // b. SELECT SQL 준비 - Connection
            String sql = "SELECT b.id, b.user_id, b.title, b.content, u.name, b.regdate, b.read_count FROM board b INNER JOIN user u ON b.user_id = u.id ORDER BY b.thread DESC LIMIT ?, ?";
            ps = conn.prepareStatement(sql);
            // c. 바인딩 - PreparedStatement
            ps.setLong(1, start); // 첫번째 물음표에 5를 바인딩한다.
            ps.setInt(2, limit);

            // d. SQL 실행 - PreparedStatement
            rs = ps.executeQuery(); // SELECT 문장을 실행, executeUpdate() - insert, update, delete

            // e. 1건씩 row를 읽어온다. row는 여러개의 컬럼으로 구성되어 있다. - ResultSet
            //    next()는 값이 있으면 true ,  값이 없으면 false를 리턴한다.
            // f. e에서 읽어오지 못하는 경우도 있다.
            while(rs.next()){
                long id = rs.getLong(1);
                long userId = rs.getLong(2);
                String title = rs.getString(3);
                String content = rs.getString(4);
                String name = rs.getString(5);
                Date regdate = rs.getDate(6);
                int readCount = rs.getInt(7);

                Board board = new Board(id, userId, name, title, content, regdate, readCount);
                list.add(board);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            // g. ResultSet, PreparedStatement, Connection 순으로 close를 한다.
            DBUtil.close(rs, ps);
        }
        return list;
    }

    @Override
    public void updateReadCount(Long id) {
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = ConnectionContextHolder.getConnection();
            if(conn != null) {
                System.out.println("conn ok!");
                System.out.println(conn.getClass().getName());
            }

            String sql = "Update board SET read_count = read_count + 1 WHERE id = ?";
            ps = conn.prepareStatement(sql);

            ps.setLong(1, id);

            ps.executeUpdate();

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public void addBoard(Board board) {
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = ConnectionContextHolder.getConnection();
            if(conn != null) {
                System.out.println("conn ok!");
                System.out.println(conn.getClass().getName());
            }

            System.out.println(board);

            String sql = "INSERT INTO board (thread, user_id, title, content) VALUES ((SELECT IFNULL(MAX(thread) + 100, 100) FROM board b),? , ?, ?)";
            ps = conn.prepareStatement(sql);

            ps.setLong(1, board.getUserId());
            ps.setString(2, board.getTitle());
            ps.setString(3,board.getContent());

            ps.executeUpdate();

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            DBUtil.close(ps);
        }
    }
/*
    @Override
    public void addReply(Board board) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            conn = ConnectionContextHolder.getConnection();
            if(conn != null) {
                System.out.println("conn ok!");
                System.out.println(conn.getClass().getName());
            }

            String sql = "INSERT INTO board (thread, depth, user_id, title, content) VALUES (?, ?, ?, ?, ?)";
            ps = conn.prepareStatement(sql);

            ps.setLong(1, board.getThread());
            ps.setLong(2, board.getId());
            ps.setLong(3,board.getUserId());
            ps.setString(4,board.getTitle());
            ps.setString(5,board.getContent());

            ps.executeUpdate();

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            DBUtil.close(ps);
        }
    }

    public Board setThreadDepth(long parentId, Board board){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = ConnectionContextHolder.getConnection();

            String sql = "SELECT thread, depth FROM board  where b.id = ?";
            ps = conn.prepareStatement(sql);

            ps.setLong(1, parentId); // 첫번째 물음표에 5를 바인딩한다.

            rs = ps.executeQuery(); // SELECT 문장을 실행, executeUpdate() - insert, update, delete

            if(rs.next()){
                long thread = rs.getLong(1);
                int depth = rs.getInt(2);

                if(thread<= (thread/100)*100 || thread >= (thread/100)*100 + 100){
                    throw new RuntimeException("thread값 에러");
                }
                if(depth >= 5){
                    throw new RuntimeException("depth 초과");
                }

                board.setThread(thread -1);
                board.setDepth(depth + 1);
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            DBUtil.close(rs, ps);
        }

        return board;
    }
*/
    @Override
    public void deleteBoard(long id) {
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = ConnectionContextHolder.getConnection();
            if(conn != null) {
                System.out.println("conn ok!");
                System.out.println(conn.getClass().getName());
            }

            String sql = "DELETE FROM board WHERE id = ?";
            ps = conn.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate(); // 입력,수정,삭제 건수 가 리턴된다.

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            DBUtil.close(ps);
        }

    }

    @Override
    public void updateBoard(Board board) {
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = ConnectionContextHolder.getConnection();
            if(conn != null) {
                System.out.println("conn ok!");
                System.out.println(conn.getClass().getName());
            }

            String sql = "Update board SET title = ? , content = ? WHERE id = ?";
            ps = conn.prepareStatement(sql);

            System.out.println(board);

            ps.setString(1, board.getTitle());
            ps.setString(2, board.getContent());
            ps.setLong(3, board.getId());
            ps.executeUpdate();

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            DBUtil.close(ps);
        }
    }
}

