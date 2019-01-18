package jdbcboard.service;

import jdbcboard.dao.UserDao;
import jdbcboard.dao.UserDaoImpl;
import jdbcboard.dto.User;
import jdbcboard.util.ConnectionContextHolder;
import jdbcboard.util.DBUtil;

import java.sql.Connection;

public class UserServiceImpl implements UserService{
    private static UserService instance = new UserServiceImpl();
    private UserServiceImpl(){}
    public static UserService getInstance(){
        return instance;
    }
    @Override
    public void addUser(User user) {
        Connection conn = null;
        UserDao userDao = new UserDaoImpl();

        try{
            conn = DBUtil.getInstance().getConnection();
            ConnectionContextHolder.setConnection(conn);
            userDao.addUser(user);
            conn.commit();
        }catch (Exception ex){
            DBUtil.rollback(conn);
            ex.printStackTrace();

        }finally {
            DBUtil.close(conn);
        }
    }

    @Override
    public long hasUser(String email, String passwd) {
        long userId = -1;

        Connection conn = null;
        UserDao userDao = new UserDaoImpl();

        try{
            conn = DBUtil.getInstance().getConnection();
            ConnectionContextHolder.setConnection(conn);
            userId = userDao.hasUser(email, passwd);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            DBUtil.close(conn);
        }
        return userId;
    }

    @Override
    public String getPasswdByEmail(String email) {
        String passwd = null;

        Connection conn = null;
        UserDao userDao = new UserDaoImpl();

        try{
            conn = DBUtil.getInstance().getConnection();
            ConnectionContextHolder.setConnection(conn);
            passwd = userDao.getPasswdByEmail(email);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            DBUtil.close(conn);
        }
        return passwd;
    }

    @Override
    public User getUserByEmail(String email) {
        User user = null;
        Connection conn = null;
        UserDao userDao = new UserDaoImpl();

        try{
            conn = DBUtil.getInstance().getConnection();
            ConnectionContextHolder.setConnection(conn);
            user = userDao.getUserByEmail(email);
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            DBUtil.close(conn);
        }
        return user;
    }
}
