package api.demo.board.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

//@Aspect = 자바 코드에서 AOP 설정

@Component
@Aspect
@Slf4j
public class LoggerAspect {

    //@Around 어노테이션으로 해당 메소드가 실행될 시점(어드바이스)를 정의(대상 메소드의 실행 전후 또는 예외 밸생 시점에 사용할 수 있는 Around 어노테이션 적용
    @Around("execution(* api.demo.board..controller.*Controller.*(..)) || execution(* api.demo.board..service.*Impl.*(..)) || execution(* api.demo.board..mapper.Mapper.*(..))")
    public Object logPrint(ProceedingJoinPoint joinPoint) throws Throwable{
        String type = "";
        String name = joinPoint.getSignature().getDeclaringTypeName();
        if(name.indexOf("Controller") > -1){
            type = "Controller \t:  ";
        }
        else if(name.indexOf("Service") > -1){
            type = "ServiceImpl \t:  ";
        }
        else if(name.indexOf("Mapper") > -1){
            type = "Mapper \t\t:  ";
        }
        log.debug(type + name + "." + joinPoint.getSignature().getName() + "()");
        return joinPoint.proceed();
    }
}
