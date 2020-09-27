package br.com.packagebase.projetoreferenciasb.controller.handler;

import br.com.packagebase.projetoreferenciasb.domain.DominioOperacao;
import br.com.packagebase.projetoreferenciasb.domain.DominioRecurso;
import br.com.packagebase.projetoreferenciasb.model.LogTrace;
import br.com.packagebase.projetoreferenciasb.utils.HttpReqRespUtils;
import br.com.packagebase.projetoreferenciasb.utils.TraceUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

import static br.com.packagebase.projetoreferenciasb.utils.TraceUtils.*;

@Log4j2
public class RequestInterceptorHandler implements HandlerInterceptor {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Override
    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response,
                             final Object handler) {
        try {
            TraceUtils.addTraceIdContextMDC(CONTEXT_API);
            log.info(":::::::::::::::::REQUEST:::::::::::::::::");
            log.info("Path: {}", request.getRequestURI());
            log.info("Method: {}", request.getMethod());
            log.info("Query {}", request.getQueryString());
        } catch (Exception e) {
            log.error(e);
        }
        return true;
    }

    @Override
    public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
                                final Object handler, final Exception ex) {
        log.info(":::::::::::::::::RESPONSE:::::::::::::::::");
        log.info("Status: {}", response.getStatus());
        log.info("Path: {}", request.getRequestURI());
        log.info("Method: {}", request.getMethod());
        log.info("Query {}", request.getQueryString());
        this.createLogTrace(request, response);
        TraceUtils.limparContextoMDC();
    }

    public void createLogTrace(HttpServletRequest request, HttpServletResponse response) {
        LogTrace logTrace = new LogTrace();
        try {
            String transactionDomain = TraceUtils.getTransaction(TRANSACTION_RESOURCE);
            logTrace.setDominio(transactionDomain != null ? DominioRecurso.valueOf(TraceUtils.getTransaction(TRANSACTION_RESOURCE)) : null);
            String transactionOperation = TraceUtils.getTransaction(TRANSACTION_OPERATION);
            logTrace.setOperacao(handleTransactionOperation(transactionOperation, request));
            String transactionId = TraceUtils.getTransaction(TRANSACTION_ID);
            logTrace.setChave(transactionId != null ? Long.valueOf(transactionId) : null);
            String transactionResponseErrors = TraceUtils.getTransaction(TRANSACTION_RESPONSE_ERRORS);
            logTrace.setResponseErrors(transactionResponseErrors);
            String transactionException = TraceUtils.getTransaction(TRANSACTION_EXCEPTION);
            logTrace.setException(transactionException);
            logTrace.setTraceId(TraceUtils.getTraceId());
            logTrace.setRequestPath(request.getRequestURI());
            logTrace.setResponseStatus(response.getStatus());
            logTrace.setRequestMethod(request.getMethod());
            logTrace.setRequestQuery(request.getQueryString());
            logTrace.setIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());
            //TODO: Capturar do SpringSecurity
            String transactionTimeBegin = TraceUtils.getTransaction(TRANSACTION_TIME_BEGIN);
            logTrace.setDataHoraInicio(transactionTimeBegin != null ? new Date(Long.parseLong(transactionTimeBegin)) : null);
            logTrace.setDataHoraFim(new Date());
            logTrace.setTempoRespostaMs(logTrace.calcularTempoResposaMilisegundos());
            logTrace.setUsuario("DEFAULT");
            applicationEventPublisher.publishEvent(logTrace);
        } catch (Exception e) {
            log.error(e);
        }
    }

    private DominioOperacao handleTransactionOperation(String transactionOperation, HttpServletRequest request) {
        if (transactionOperation == null) {
            switch (request.getMethod()) {
                case "GET":
                    return DominioOperacao.CONSULTAR;
                case "POST":
                    return DominioOperacao.CADASTRAR;
                case "PUT":
                    return DominioOperacao.EDITAR;
                case "DELETE":
                    return DominioOperacao.EXCLUIR;
            }
        }
        return DominioOperacao.valueOf(TraceUtils.getTransaction(TRANSACTION_OPERATION));
    }

}
