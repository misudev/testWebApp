package jdbcboard.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtil00 {
    private static final String driverClassname = "com.mysql.cj.jdbc.Driver";
    private static final String driverURL = "jdbc:mysql://localhost:3306/firstDB?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul";
    private static final String dbUserId = "testuser";
    private static final String dbUserPassword = "1234";

    public static Connection getConnection(){
        Connection conn = null;
        try{
            Class.forName(driverClassname);
            conn = DriverManager.getConnection(driverURL, dbUserId, dbUserPassword);
        }catch(Exception ex){
            ex.printStackTrace();
            throw new RuntimeException("DB에 연결할 수 없습니다.");
        }
        return conn;
    }

    public static void close(ResultSet rs, PreparedStatement ps, Connection conn){
        try{ rs.close(); } catch(Exception ignore){}
        close(ps, conn);
    }

    public static void close(PreparedStatement ps, Connection conn){
        try{ ps.close(); } catch(Exception ignore){}
        try{ conn.close(); } catch(Exception ignore){}
    }
}
