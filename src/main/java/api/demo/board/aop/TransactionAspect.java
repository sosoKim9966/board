package api.demo.board.aop;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.interceptor.MatchAlwaysTransactionAttributeSource;
import org.springframework.transaction.interceptor.RollbackRuleAttribute;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.Collections;


@Configuration
public class TransactionAspect {

    //AOP를 이용해 트랜잭션을 설정하면 새로운 클래스나 메소드가 추가될 때 따로 어노테이션을 붙이지 않아도 자동적으로 트랜잭션 처리가 됨.

    private static final String AOP_TRANSACTION_METHOD_NAME = "*";   //트랜잭션을 설정할 때 사용되는 설정값을 상수로 선언합니다.
    private static final String AOP_TRANSACTION_EXPRESSION = "execution(* api.demo.board..service.*Impl.*(..))"; //트랜잭션 이름을 설정합니다. 트랜잭션 모니터에서 트랜잭션 이름을 확인 할수 있음

    @Autowired
    private TransactionManager transactionManager;

    @Bean
    public TransactionInterceptor transactionAdvice(){
        MatchAlwaysTransactionAttributeSource source = new MatchAlwaysTransactionAttributeSource();
        RuleBasedTransactionAttribute transactionAttribute = new RuleBasedTransactionAttribute();
        transactionAttribute.setName(AOP_TRANSACTION_METHOD_NAME);
        transactionAttribute.setRollbackRules(Collections.singletonList(new RollbackRuleAttribute(Exception.class))); //트랜잭션에서 롤백을 하는 룰을 설정 예외발생 => 롤백백
        source.setTransactionAttribute(transactionAttribute);

        return new TransactionInterceptor(transactionManager, source);
    }

    @Bean
    public Advisor transaction(){
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression(AOP_TRANSACTION_EXPRESSION);         //AOP 포인트컷 설정. 비지니스 로직이 수행되는 모든 ServiceImpl클래스의 모든 메소드 지정

        return new DefaultPointcutAdvisor(pointcut, transactionAdvice());
    }


}
