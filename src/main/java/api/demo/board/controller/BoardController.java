package api.demo.board.controller;

import api.demo.board.dto.BoardDto;
import api.demo.board.dto.BoardFileDto;
import api.demo.board.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.net.URLEncoder;
import java.util.List;

@Slf4j
@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @RequestMapping("/board/openBoardList.do")
    public ModelAndView openBoardList() throws Exception{
        log.debug("/board/openBoardList start");
        ModelAndView mv = new ModelAndView("/board/boardList");
        List<BoardDto> list = boardService.selectBoardList();
        mv.addObject("list", list);

        return mv;
    }

    @RequestMapping("/board/openBoardWrite.do")
    public String openBoardWrite() throws Exception {
        log.debug("/board/openBoardWrite.do start");
        return "/board/boardWrite";
    }

    @RequestMapping("/board/insertBoard.do")
    public String insertBoard(BoardDto board, MultipartHttpServletRequest multipartHttpServletRequest) throws Exception{
        log.debug("/board/insertBoard.do start" + board);
        boardService.insertBoard(board, multipartHttpServletRequest);
        return "redirect:/board/openBoardList.do";
    }

    @RequestMapping("/board/openBoardDetail.do")
    public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception{
        log.debug("/board/openBoardDetail.do start boardIdx =>" + boardIdx);
        ModelAndView mv = new ModelAndView("/board/boardDetail");

        BoardDto board = boardService.selectBoardDetail(boardIdx);
        mv.addObject("board", board);
        return mv;
    }

    @RequestMapping("/board/updateBoard.do")
    public String updateBoard(BoardDto board) throws Exception{
        log.debug("/board/updateBoard.do start board =>" + board);
        boardService.updateBoard(board);
        return "redirect:/board/openBoardList.do";
    }

    @RequestMapping("/board/deleteBoard.do")
    public String deleteBoard(int boardIdx) throws Exception{
        log.debug("board/deleteBoard.do start boardIdx => " + boardIdx);
        boardService.deleteBoard(boardIdx);
        return "redirect:/board/openBoardList.do";
    }

    @RequestMapping("/board/downloadBoardFile.do")
    public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse response) throws Exception{
        BoardFileDto boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
        if(ObjectUtils.isEmpty(boardFile) == false) {
            String fileName = boardFile.getOriginalFileName();

            //조회된 파일의 정보 중 storedFilePath 값을 이용해 실제 저장되어 잇는 파일을 읽어온 후, byte[[ 형태로 변환
            byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));

            response.setContentType("application/octet-stream");
            response.setContentLength(files.length);
            response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");

            response.getOutputStream().write(files);
            response.getOutputStream().flush();
            response.getOutputStream().close();
        }
    }

    @RequestMapping("/board/deleteBoardFile.do")
    public String deleteBoardFile(@RequestParam int idx, @RequestParam int boardIdx) throws Exception{
        boardService.deleteBoardFile(idx, boardIdx);
        return "redirect:/board/openBoardDetail.do?boardIdx="+boardIdx;
    }
}
