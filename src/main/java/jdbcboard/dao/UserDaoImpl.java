package jdbcboard.dao;

import jdbcboard.dto.User;
import jdbcboard.util.ConnectionContextHolder;
import jdbcboard.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserDaoImpl  implements UserDao{
    @Override
    public User getUser(long id) {
        User user = null; // return할 타입을 선언한다.
/*
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // a. DB 연결 - Connection
            //    DB연결을 하려면 필요한 정보가 있다. Driver classname, DB URL, DB UserId , DB User Password
            conn = DBUtil.getInstance().getConnection();

            // b. SELECT SQL 준비 - Connection
            String sql = "SELECT name, passwd, email FROM user where id = ?";
            ps = conn.prepareStatement(sql);
            // c. 바인딩 - PreparedStatement
            ps.setLong(1, id); // 첫번째 물음표에 5를 바인딩한다.

            // d. SQL 실행 - PreparedStatement
            rs = ps.executeQuery(); // SELECT 문장을 실행, executeUpdate() - insert, update, delete

            // e. 1건의 row를 읽어온다.
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
            DBUtil.close(rs, ps, conn);
        }
*/
        return user;

    }

    @Override
    public List<User> getUsers(long start, int limit) {
        return null;
    }

    @Override
    public void addUser(User user) {
        Connection conn = null;
        PreparedStatement ps = null;

        try{
            conn = ConnectionContextHolder.getConnection();
            if(conn != null) {
                System.out.println("conn ok!");
                System.out.println(conn.getClass().getName());
            }

            String sql = "INSERT INTO user (name, passwd, email) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(sql);

            ps.setString(1, user.getName());
            ps.setString(2, user.getPasswd());
            ps.setString(3, user.getEmail());

            ps.executeUpdate();

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            DBUtil.close(ps);
        }
    }

    @Override
    public long hasUser(String email, String passwd) {
        long userId = -1;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = conn = ConnectionContextHolder.getConnection();

            String sql = "SELECT id FROM user WHERE email=? AND passwd=?";
            ps = conn.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, passwd);

            System.out.println("email : "+email + " passwd: "+ passwd);

            rs = ps.executeQuery();

            if(rs.next()){
                userId = rs.getLong(1);
            }else{
                userId = -1;
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            DBUtil.close(rs,ps);
        }
        return userId;
    }
}
