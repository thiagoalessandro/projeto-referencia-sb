package br.com.packagebase.projetoreferenciasb.config;

import br.com.packagebase.projetoreferenciasb.annotation.LogApp;
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
public class LogTransacionalConfig {

    @AfterThrowing(pointcut = "execution(* br.com.packagebase.projetoreferenciasb.*.*.*(..))", throwing = "e")
    public void before(Exception e){
        if(!(e instanceof ValidationException)){
            TraceUtils.setTransaction(TraceUtils.TRANSACTION_EXCEPTION, ExceptionUtils.getStackTrace(e.getCause()));
        }
    }

    @Pointcut("@annotation(logApp)")
    public void callAt(LogApp logApp) {
   }

    @Around("callAt(logApp)")
    public Object around(ProceedingJoinPoint pjp,
                         LogApp logApp) throws Throwable {
        TraceUtils.setTransaction(TraceUtils.TRANSACTION_RESOURCE, logApp.recurso().name());
        TraceUtils.setTransaction(TraceUtils.TRANSACTION_OPERATION, logApp.operacao().name());
        return pjp.proceed();
    }

}
