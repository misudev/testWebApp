package jdbcboard.exam;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jdbcboard.dto.Board;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

public class DbcpExam {


    public static void main(String[] args){

        //HikariConfig config = new HikariConfig();
        //config.setDriverClassName("com.mysql.cj.jdbc.Driver");
        //config.setJdbcUrl("jdbc:mysql://localhost:3306/firstDB?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Seoul");
        //config.setUsername("testuser");
        //config.setPassword("1234");

        String configFile = "/datasource.properties";
        HikariConfig config = new HikariConfig(configFile);

        DataSource ds = new HikariDataSource(config);

        int start = 0;
        int limit = 2;

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ds.getConnection();

            if (conn != null)
                System.out.println("connection is ok");

            String sql = "SELECT b.id, b.userId, b.title, b.content, u.name, b.regdate, b.read_count FROM board b INNER JOIN user u ON b.user_id = u.id ORDER BY b.thread DESC LIMIT ?, ?";
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
                long userId = rs.getLong(2);
                String title = rs.getString(3);
                String content = rs.getString(4);
                String name = rs.getString(5);
                Date regdate = rs.getDate(6);
                int readCount = rs.getInt(7);

                Board board = new Board(id, userId, name, title, content, regdate, readCount);
                System.out.println(board);
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }finally {
            try{
                rs.close();
            }catch(Exception ignore){}
            try{
                ps.close();
            }catch(Exception ignore){}
            try{
                conn.close();
            }catch(Exception ignore){}
        }

    }
}
