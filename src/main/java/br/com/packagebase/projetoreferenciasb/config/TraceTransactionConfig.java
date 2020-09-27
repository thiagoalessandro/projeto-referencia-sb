package br.com.packagebase.projetoreferenciasb.config;

import br.com.packagebase.projetoreferenciasb.annotation.TraceTransaction;
import br.com.packagebase.projetoreferenciasb.exception.ValidationException;
import br.com.packagebase.projetoreferenciasb.utils.TraceUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Aspect
@Configuration
public class TraceTransactionConfig {

    @AfterThrowing(pointcut = "execution(* br.com.packagebase.projetoreferenciasb.*.*.*(..))", throwing = "e")
    public void before(Exception e){
        if(!(e instanceof ValidationException)){
            TraceUtils.setTransaction(TraceUtils.TRANSACTION_EXCEPTION, ExceptionUtils.getStackTrace(e.getCause()));
        }
    }

    @Pointcut("@annotation(traceTransaction)")
    public void callAt(TraceTransaction traceTransaction) {
   }

    @Around("callAt(traceTransaction)")
    public Object around(ProceedingJoinPoint pjp,
                         TraceTransaction traceTransaction) throws Throwable {
        TraceUtils.setTransaction(TraceUtils.TRANSACTION_RESOURCE, traceTransaction.recurso().name());
        TraceUtils.setTransaction(TraceUtils.TRANSACTION_OPERATION, traceTransaction.operacao().name());
        return pjp.proceed();
    }

}
