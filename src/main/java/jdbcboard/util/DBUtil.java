package jdbcboard.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBUtil {

    private static HikariConfig config = null;
    private static DataSource ds = null;

    // 2) private static 하게 자기 자신 인스턴스를 참조하는 변수를 선언.
    private static DBUtil instance = new DBUtil();
    // 1) private한 생성자
    private DBUtil(){
//        HikariConfig config = new HikariConfig();
//        config.setDriverClassName("com.mysql.cj.jdbc.Driver");
//        config.setJdbcUrl("jdbc:mysql://localhost:3306/firstDB?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul");
//        config.setUsername("testuser");
//        config.setPassword("1234");

        String configFile = "/datasource.properties";
        HikariConfig config = new HikariConfig(configFile);

        ds = new HikariDataSource(config);
    }
    // 3) 2에서 선언한 객체를 리턴하는 static 메소드를 제공
    public static DBUtil getInstance(){
        return instance;
    }

    public Connection getConnection(){
        Connection conn = null;
        try {
            conn = ds.getConnection();
            conn.setAutoCommit(false);
        }catch(Exception ex){
            ex.printStackTrace(); // 로그를 남기는 코드가 있어야 한다.
            throw new RuntimeException("DB연결을 할 수 없습니다.");
        }
        return conn;
    }

    public static void rollback(Connection conn){
        try{ conn.rollback(); } catch(Exception ignore){}
    }
    public static void close(Connection conn){
        try{ conn.close(); } catch(Exception ignore){}
    }

    public static void close(ResultSet rs, PreparedStatement ps){
        try{ rs.close(); } catch(Exception ignore){}
        close(ps);
    }

    public static void close(PreparedStatement ps){
        try{ ps.close(); } catch(Exception ignore){}
    }
}

