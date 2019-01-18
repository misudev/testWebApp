package jdbcboard.dao;

import jdbcboard.dto.User;

import java.util.List;

public interface UserDao {
    //public User getUser(long id);
   // public List<User> getUsers(long start, int limit);

    void addUser(User user);
    long hasUser(String email, String passwd);
    User getUserByEmail(String email);
    String getPasswdByEmail(String email);

}
