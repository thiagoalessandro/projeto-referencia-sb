package br.com.packagebase.projetoreferenciasb.service;

import br.com.packagebase.projetoreferenciasb.model.LogTrace;
import br.com.packagebase.projetoreferenciasb.repository.LogTraceRepository;
import br.com.packagebase.projetoreferenciasb.utils.TraceUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Log4j2
@Service
public class LogTraceService {

    private final LogTraceRepository repository;

    LogTraceService(LogTraceRepository repository) {
        this.repository = repository;
    }

    @Async
    @EventListener(LogTrace.class)
    @TransactionalEventListener(phase = TransactionPhase.BEFORE_COMMIT)
    @Transactional(propagation = Propagation.REQUIRED)
    public void onCreate(LogTrace logTrace){
        log.debug("LogTrace {}...", logTrace.toString());
        repository.save(logTrace);
    }

}
