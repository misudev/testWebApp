package jdbcboard.exam;

import jdbcboard.dao.BoardDao;
import jdbcboard.dao.BoardDaoImpl;
import jdbcboard.dto.Board;

import java.sql.*;
import java.util.List;

public class SelectExam {

    public static void main(String[] args){
        BoardDao boardDao = new BoardDaoImpl();
        Board board = boardDao.getBoard(3L);
        System.out.println(board);

    }
}
