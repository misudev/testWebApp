package jdbcboard.dao;

import jdbcboard.dto.Board;

import java.util.List;

public interface BoardDao {
    public Board getBoard(long id);

    public List<Board> getBoards(long start, int limit);


    void updateReadCount(Long id);

    void addBoard(Board board);

   // void addReply(Board board, long parentId);

    void deleteBoard(long id);

    void updateBoard(Board board);

    Board getThreadDepth(long id);

    void addReply(Board board);

    void updateThreadMinus(long thread);

    void updateThreadPlus(long min, long max);

    boolean existReply(long thread);

    //manage 테이블
    long getMaxThread();

    long getCountBoard();

    void updateMaxThread();

    void updateMaxThreadMinus();

    void updateCountBoard();

    void updateCountBoardMinus();

    // search

    public List<Board> getBoardsByTitle(String keyword, long start, int limit);

    public List<Board> getBoardsByContent(String keyword, long start, int limit);

    public long countByTitle(String keyword);

    public long countByContent(String keyword);

    //public static final String SELECT_THREAD_BY_ID =
    //            "SELECT thread, depth FROM board where id = ?";
    //    public static final String INSERT_REPLY =
    //            "INSERT INTO board (thread, depth, user_id, nickname, title, content) VALUES (?,?,?,?,?,?)";
    //    public static final String UPDATE_THREAD_MINUS =
    //            "UPDATE board SET thread = thread - 1 WHERE thread > ? AND thread < ?;";
    //    public static final String UPDATE_THREAD_PLUS =
    //            "UPDATE board SET thread = thread + 1 WHERE thread > ? AND thread < ?;";
}
