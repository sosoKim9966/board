package api.demo.board.controller;

import api.demo.board.dto.BoardDto;
import api.demo.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // @Controller + @ResponseBody : 해당 API 응답 결과를 웹 바디를 이용해 보내줌 => 결과값을 JSON 형식으로 만들어줌
public class RestBoardApiController {

    @Autowired
    BoardService boardService;

    @RequestMapping(value="/api/board", method = RequestMethod.GET)
    public List<BoardDto> openBoardList() throws Exception{
        return boardService.selectBoardList();
    }

    @RequestMapping(value="/api/board/write", method = RequestMethod.POST)
    public void insertBoard(@RequestBody BoardDto board) throws Exception{
        boardService.insertBoard(board, null);
    }

    @RequestMapping(value="/api/board/{boardIdx}", method = RequestMethod.GET)
    public BoardDto openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception{
        return boardService.selectBoardDetail(boardIdx);
    }

    @RequestMapping(value="/api/board/{boardIdx}", method = RequestMethod.PUT)
    public String updateBoard(@RequestBody BoardDto board) throws Exception{
        boardService.updateBoard(board);
        return "redirect:/board";
    }

    @RequestMapping(value="/api/board/{boardIdx}", method = RequestMethod.DELETE)
    @DeleteMapping(value="/board/{boardIdx}")
    public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception{
        boardService.deleteBoard(boardIdx);
        return "redirect:/board";
    }
}
