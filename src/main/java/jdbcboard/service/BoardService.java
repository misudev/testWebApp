package jdbcboard.service;

import jdbcboard.dto.Board;

import java.util.List;

public interface BoardService {
    // page에 해당하는 목록을 읽어온다.
    // 전체 건수를 읽어온다.
    // 글을 읽어온다. (글읽기 + 조회수증가)
    // 글을 삭제한다.
    public List<Board> getBoards(int page);
    public Board getBoard(Long id);
    public long getBoardCount();
    public void deleteBoard(Long id, Long sigendId);
    public void addBoard(Board board);
    public void updateBoard(Board board);

    public void addReply(long parentId, Board board);
}
