package jdbcboard.dto;

import java.util.Date;
import java.time.LocalDateTime;


// VO or DTO - 하나의 값을 가지는 객체
public class Board {
    // 필드
    private Long id;
    private Long userId;
    private String name;
    private String title;
    private String content;
    private Date regdate;
    private int readCount;

    private Long thread;
    private int depth;

    // 자기 자신의 생성자를 호출하는 방법
    // LocaDateTime 에 대하여 공부.
    public Board(){
        regdate = new Date(); // 현재 시간을 저장.
    }

    public Board(Long userId, String title, String content) {
        this();
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    public Board(Long id, Long userId, String name, String title, String content, Date regdate, int readCount) {
        this(userId, title, content);
        this.id = id;
        this.name = name;
        this.regdate = regdate;
        this.readCount = readCount;
    }
    // getter , setter 메소드
    // id 프로퍼티
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


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getThread() {
        return thread;
    }

    public void setThread(Long thread) {
        this.thread = thread;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", regdate=" + regdate +
                ", readCount=" + readCount +
                '}';
    }
}

