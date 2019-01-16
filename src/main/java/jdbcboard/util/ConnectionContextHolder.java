package jdbcboard.util;

import java.sql.Connection;

public class ConnectionContextHolder {

    private static ThreadLocal<Connection> threadLocal
            = new ThreadLocal<>();

    public static void setConnection(Connection connection){
        threadLocal.set(connection);
    }

    public static Connection getConnection(){
        return threadLocal.get();
    }
}
