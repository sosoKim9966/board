package api.demo.board.controller;

import api.demo.board.dto.BoardDto;
import api.demo.board.mapper.BoardMapper;
import api.demo.board.service.BoardService;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@Slf4j
@Controller
public class AjaxController {

    @Autowired
    private BoardService boardService;

    @RequestMapping(value="/get", method = RequestMethod.GET)
    public String ajaxUpdateGet() throws Exception{
        return "/board/ajaxBoardWrite";
    }

    @RequestMapping(value="/modify", method = RequestMethod.POST)
    public String ajaxUpdate(@RequestBody BoardDto board) throws Exception{
        log.debug(board.getTitle(), board.getContent());
        boardService.insertBoard(board, null);
        return "redirect:/board2";
    }

//    @RequestMapping(value="/test", method = RequestMethod.POST)
//    public String test(@RequestParam Map<String, Object> param) throws Exception{
//        String title = (String)param.get("title");
//        String content = (String)param.get("content");
//        boardService.insertBoard2(title, content);
//        return "redirect:/board2";
//    }

    @RequestMapping(value = "/write", method = RequestMethod.GET)
    public String write(Locale locale, Model model) {
        return "/board/ajaxBoardWrite";
    }

    @RequestMapping(value = "/insertOk", method = RequestMethod.POST)
    @ResponseBody
    public HashMap<String, String> insertOk(Locale locale, Model model, HttpServletRequest request) throws Exception{
        HashMap<String, String> list = new HashMap<String, String>();
        String title = request.getParameter("title");
        String content = request.getParameter("content");

        BoardDto boardDto = new BoardDto();

        boardDto.setTitle(title);
        boardDto.setContent(content);

        int result = boardService.insertBoard3(boardDto);
        String Msg;
        String Code;

        if(result == 1) {
            Msg = "성공";
            Code = "0";
            list.put("Msg", Msg);
            list.put("Code", Code);
        }
        if (result != 1){
            Msg = "실패";
            Code = "1";
            list.put("Msg", Msg);
            list.put("Code", Code);
        }
        return list;
    }

    @RequestMapping(value = "/listOn", method = RequestMethod.GET)
    @ResponseBody
    public HashMap<String, Object> listOn() throws Exception{
        HashMap<String, Object> resultMap = new HashMap<String, Object>();
        List<BoardDto> boardList = boardService.selectBoardList();
        String Msg;
        String Code;

        if(!boardList.isEmpty()) {
            Msg = "성공";
            Code = "1";
            resultMap.put("data", boardList);
        } else {
            Msg = "실패";
            Code = "0";
        }

        resultMap.put("Msg", Msg);
        resultMap.put("Code", Code);
        return resultMap;
    }



    @RequestMapping(value = "/board2", method = RequestMethod.GET)
    public ModelAndView openBoardList() throws Exception{

        ModelAndView mv = new ModelAndView("/board/restBoardList");
        List<BoardDto> list = boardService.selectBoardList();
        mv.addObject("list", list);

        return mv;
    }


}
