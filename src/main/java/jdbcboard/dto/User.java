package jdbcboard.dto;

public class User {
    //필드
    private Long id;
    private String name;
    private String nickname;
    private String passwd;
    private String email;


    public User(String name, String nickname, String passwd, String email){
        this.name = name;
        this.nickname = nickname;
        this.passwd = passwd;
        this.email = email;
    }

    public User(Long id, String nickname, String name, String passwd, String email){
        this(name, nickname, passwd, email);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", passwd='" + passwd + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
