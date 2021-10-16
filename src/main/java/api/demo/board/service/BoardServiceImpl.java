package api.demo.board.service;

import api.demo.board.common.FileUtils;
import api.demo.board.dto.BoardDto;
import api.demo.board.dto.BoardFileDto;
import api.demo.board.mapper.BoardMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.List;

@Slf4j
@Service
//@Transactional   //인터페이스나 클래스, 메소드에서 사용할 수 있다. 어노테이션이 적용된 대상은 설정된 트랜잭션 빈에 의해 트랜잭션 처리
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardMapper boardMapper;

    @Autowired
    private FileUtils fileUtils;

    @Override
    public List<BoardDto> selectBoardList() throws Exception{
        log.debug("BoardService selectBoardList() start");
        return boardMapper.selectBoardList();
    }

    @Override
    public List<BoardDto> selectBoardList1(BoardDto boardDto) throws Exception{
        log.debug("BoardService selectBoardList() start");
        return boardMapper.selectBoardList1(boardDto);
    }
    @Override
    public void insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        log.debug("BoardService insertBoard(BoardDto board) start board => " + board);
        boardMapper.insertBoard(board);
        List<BoardFileDto> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletRequest);
        if(CollectionUtils.isEmpty(list) == false){
            boardMapper.insertBoardFileList(list);
        }

    }

    @Override
    public void insertBoard2(String title, String content){
       boardMapper.insertBoard2(title, content);
    }

    @Override
    public int insertBoard3(BoardDto board) throws Exception{
        log.debug("BoardService insertBoard(BoardDto board) start board => " + board);
        boardMapper.insertBoard3(board);
        return 1;
    }

    @Override
    public BoardDto selectBoardDetail(int boardIdx) throws Exception{
        log.debug("BoardService selectBoardDetail(int boardIdx) start boardIdx => " + boardIdx);
        BoardDto board = boardMapper.selectBoardDetail(boardIdx);
        List<BoardFileDto> fileList = boardMapper.selectBoardFileList(boardIdx);
        board.setFileList(fileList);
        boardMapper.updateHitCount(boardIdx);
        return board;
    }

    @Override
    public void updateBoard(BoardDto board) throws Exception{
        log.debug("BoardService updateBoard(BoardDto board) start board => " + board);
        boardMapper.updateBoard(board);
    }

    @Override
    public void deleteBoard(int boardIdx) throws Exception{
        log.debug("BoardService deleteBoard(int boardIdx) start boardIdx => " + boardIdx);
        boardMapper.deleteBoard(boardIdx);
    }

    @Override
    public BoardFileDto selectBoardFileInformation(int idx, int boardIdx) throws Exception{
        return boardMapper.selectBoardFileInformation(idx, boardIdx);
    }

    @Override
    public void deleteBoardFile(int idx, int boardIdx) throws Exception{
        boardMapper.deleteBoardFile(idx, boardIdx);
    }
}
