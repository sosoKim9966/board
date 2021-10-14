package api.demo.board.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice  //해당클래스가 예외처리 클래스임을 알림
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class) //해당 메소드에서 처리할 예외를 지정(여기서는 exception.class로 설정하여 모든 예외를 처리함(실제로는 절대 안함!!)
    public ModelAndView defaultExceptionHandler(HttpServletRequest request, Exception exception){ // 이 메소드는 가장 마지막에 있어야 함
        ModelAndView mv = new ModelAndView("/error/error_default");  //예외 발생 시, 보여줄 화면 지정.
        mv.addObject("exception", exception);

        log.error("exception", exception);  //에러 로그 출력

        return mv;
    }

}
