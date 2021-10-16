package api.demo.board.mapper;

import api.demo.board.dto.BoardDto;
import api.demo.board.dto.BoardFileDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<BoardDto> selectBoardList() throws Exception;
    List<BoardDto> selectBoardList1(BoardDto boardDto) throws Exception;
    void insertBoard(BoardDto board) throws Exception;
    void insertBoard2(@Param("title") String title, @Param("content") String content);
    void insertBoard3(BoardDto boardDto) throws Exception;
    void updateHitCount(int boardIdx) throws Exception;
    BoardDto selectBoardDetail(int boardIdx) throws Exception;
    void updateBoard(BoardDto board) throws Exception;
    void deleteBoard(int boardIdx) throws Exception;

    void insertBoardFileList(List<BoardFileDto> list) throws Exception;
    List<BoardFileDto> selectBoardFileList(int boardIdx) throws Exception;
    //@Param : 쿼리의 파라미터가 2~3개인 경우 이를 위해 DTO를 만들수 없어서 이 어노테이션을 사용하여 해당 파라미터들이 Map에 저장되어 DTO를 만들지 않아도 여러 파라미터를 전달 할 수 있음
    BoardFileDto selectBoardFileInformation(@Param("idx") int idx, @Param("boardIdx") int boardIdx) throws Exception;
    void deleteBoardFile(@Param("idx") int idx, @Param("boardIdx") int boardIdx);

}
