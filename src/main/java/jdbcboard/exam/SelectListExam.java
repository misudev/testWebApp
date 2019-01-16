package jdbcboard.exam;

import java.sql.*;
//1건의 데이터를 요청하는 경우
//조건에 해당하는 데이터가 없을 수 있다.
public class SelectListExam {
    public static void main(String[] args){
        // driver class이름도 JDBC Driver에 따라 다르다.
        final String driverClassname = "com.mysql.cj.jdbc.Driver";
        // driver URL형식 Database마다 다르다.
        final String driverURL = "jdbc:mysql://localhost:3306/firstDB?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul";

        final String dbUserId = "testuser";
        final String dbUserPassword = "1234";

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        long start = 0;
        int limit = 3;

        try{
            // a. DB 연결 - Connection
            //    DB연결을 하려면 필요한 정보가 있다. Driver classname, DB URL, DB UserId , DB User Password
            Class.forName(driverClassname); // driver class가 로딩.
            conn = DriverManager.getConnection(driverURL, dbUserId, dbUserPassword);
            if(conn != null) {
                System.out.println("conn ok!");
                System.out.println(conn.getClass().getName());
            }
            // b. SELECT SQL 준비 - Connection
            String sql = "select id, thread, depth, user_id, title,content,name,regdate,read_count from board ORDER BY id  ASC LIMIT ?,?";
            ps = conn.prepareStatement(sql);
            // c. 바인딩 - PreparedStatement
            ps.setLong(1, start); // 첫번째 물음표에 5를 바인딩한다.
            ps.setInt(2, limit);

            // d. SQL 실행 - PreparedStatement
            rs = ps.executeQuery(); // SELECT 문장을 실행, executeUpdate() - insert, update, delete

            // e. 1건의 row를 읽어온다. row는 여러개의 컬럼으로 구성되어 있다. - ResultSet
            // f. e에서 읽어오지 못하는 경우도 있다.
            while(rs.next()){
                long id = rs.getLong(1);
                long thread = rs.getLong(2);
                int depth = rs.getInt(3);
                long user_id = rs.getLong(4);
                String title = rs.getString(5);
                String content = rs.getString(6);
                String name = rs.getString(7);
                Date regdate = rs.getDate(8);
                int readCount = rs.getInt(9);

                System.out.println(id + "," + thread + ","+ depth + "," + user_id + "," +title + ", " + content + ", " + name + ", " + regdate + ", " + readCount);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            // g. ResultSet, PreparedStatement, Connection 순으로 close를 한다.
            try{ rs.close(); } catch(Exception ignore){}
            try{ ps.close(); } catch(Exception ignore){}
            try{ conn.close(); } catch(Exception ignore){}
        }
    }
}
