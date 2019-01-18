package jdbcboard.dao;

public class UserDaoSQL {
    public static final String INSERT = "INSERT INTO user (name, nickname, passwd, email) VALUES (?, ?, ?, ?)";

    public static final String SELECT_BY_EMAIL = "SELECT passwd FROM user WHERE email = ?";

    public static final String SELECT_USER_BY_EMAIL = "SELECT id, name, nickname, passwd FROM user WHERE email = ?";
}
