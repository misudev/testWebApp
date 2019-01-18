package jdbcboard.service;

import jdbcboard.dto.User;

public interface UserService {
    // 사용자를 추가한다.
    // 해당 email, pw의 user가 있는지 확인한다.
    public void addUser(User user);
    public long hasUser(String email, String passwd);

    String getPasswdByEmail(String email);
    User getUserByEmail(String passwd);
}
