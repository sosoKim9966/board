package api.demo.board.service;

import api.demo.board.dto.BoardDto;
import api.demo.board.dto.BoardFileDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;

public interface BoardService {

    List<BoardDto> selectBoardList() throws Exception;
    List<BoardDto> selectBoardList1(BoardDto boardDto) throws Exception;
    void insertBoard(BoardDto boardDto, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception;
    void insertBoard2(String title, String content) throws Exception;
    int insertBoard3(BoardDto boardDto) throws Exception;
    BoardDto selectBoardDetail(int boardIdx) throws Exception;
    void updateBoard(BoardDto board) throws Exception;
    void deleteBoard(int boardIdx) throws Exception;

    BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception;
    void deleteBoardFile(int idx, int boardIdx) throws Exception;
}
