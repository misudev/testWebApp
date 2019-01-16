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
}
