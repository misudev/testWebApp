package jdbcboard.dao;

import jdbcboard.dto.User;
import jdbcboard.util.ConnectionContextHolder;
import jdbcboard.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class UserDaoImpl implements UserDao{

    @Override
    public User getUserByEmail(String email) {
        User user = null; // return할 타입을 선언한다.
        Connection conn = null;

        try {
            conn = conn = ConnectionContextHolder.getConnection();

            try(PreparedStatement ps = conn.prepareStatement(UserDaoSQL.SELECT_USER_BY_EMAIL);) {

                ps.setString(1, email);

                try(ResultSet rs = ps.executeQuery();) {
                    if (rs.next()) {
                        Long id = rs.getLong(1);
                        String name = rs.getString(2);
                        String nickname = rs.getString(3);
                        String passwd = rs.getString(4);

                        user = new User(id, nickname, name, passwd, email);
                    }
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return user;

    }




    @Override
    public void addUser(User user) {
        Connection conn = null;
        try{
            conn = ConnectionContextHolder.getConnection();
            try(PreparedStatement ps = conn.prepareStatement(UserDaoSQL.INSERT);) {

                ps.setString(1, user.getName());
                ps.setString(2, user.getNickname());
                ps.setString(3, user.getPasswd());
                ps.setString(4, user.getEmail());

                ps.executeUpdate();
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public long hasUser(String email, String passwd) {
        long userId = -1;

        Connection conn = null;

        try {
            conn = conn = ConnectionContextHolder.getConnection();

            String sql = "SELECT id FROM user WHERE email=? AND passwd=?";

            try(PreparedStatement ps = conn.prepareStatement(sql);) {

                ps.setString(1, email);
                ps.setString(2, passwd);

                System.out.println("email : " + email + " passwd: " + passwd);

                try(ResultSet rs = ps.executeQuery();) {

                    if (rs.next()) {
                        userId = rs.getLong(1);
                    } else {
                        userId = -1;
                    }
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }
        return userId;
    }

    @Override
    public String getPasswdByEmail(String email) {
        String passwd = null;
        Connection conn = null;

        try {
            conn = conn = ConnectionContextHolder.getConnection();

            try(PreparedStatement ps = conn.prepareStatement(UserDaoSQL.SELECT_BY_EMAIL);) {

                ps.setString(1, email);

               // System.out.println("email : " + email + " passwd: " + passwd);

                try(ResultSet rs = ps.executeQuery();) {
                    if (rs.next()) {
                        passwd = rs.getString(1);
                    }
                }
            }

        }catch(Exception ex){
            ex.printStackTrace();
        }

        return passwd;
    }
}
