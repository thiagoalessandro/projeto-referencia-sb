package br.com.packagebase.projetoreferenciasb.controller.handler;

import br.com.packagebase.projetoreferenciasb.domain.DominioOperacao;
import br.com.packagebase.projetoreferenciasb.domain.DominioRecurso;
import br.com.packagebase.projetoreferenciasb.model.LogTransacional;
import br.com.packagebase.projetoreferenciasb.utils.HttpReqRespUtils;
import br.com.packagebase.projetoreferenciasb.utils.TraceUtils;
import lombok.extern.log4j.Log4j2;
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
        this.createLogTransacional(request, response);
        TraceUtils.limparContextoMDC();
    }

    public void createLogTransacional(HttpServletRequest request, HttpServletResponse response) {
        //TODO Ideal que log transacional seja enviado via mensagem (Ex: kafka)
        try {
            String transactionDomain = TraceUtils.getTransaction(TRANSACTION_RESOURCE);
            String transactionOperation = TraceUtils.getTransaction(TRANSACTION_OPERATION);
            String transactionId = TraceUtils.getTransaction(TRANSACTION_ID);
            String transactionResponseErrors = TraceUtils.getTransaction(TRANSACTION_RESPONSE_ERRORS);
            String transactionException = TraceUtils.getTransaction(TRANSACTION_EXCEPTION);
            String transactionRevision = TraceUtils.getTransaction(TRANSACTION_REVISION);
            String transactionTimeBegin = TraceUtils.getTransaction(TRANSACTION_TIME_BEGIN);

            LogTransacional logTransacional = LogTransacional.builder()
                    .dominio(transactionDomain != null ? DominioRecurso.valueOf(TraceUtils.getTransaction(TRANSACTION_RESOURCE)) : null)
                    .operacao(transactionOperation != null ? DominioOperacao.valueOf(transactionOperation) : null)
                    .chave(transactionId != null ? Long.valueOf(transactionId) : null)
                    .responseErrors(transactionResponseErrors)
                    .exception(transactionException)
                    .revisao(transactionRevision != null ? Long.valueOf(transactionRevision) : null)
                    .traceId(TraceUtils.getTraceId())
                    .requestPath(request.getRequestURI())
                    .responseStatus(response.getStatus())
                    .requestMethod(request.getMethod())
                    .requestQuery(request.getQueryString())
                    .ip(HttpReqRespUtils.getClientIpAddressIfServletRequestExist())
                    .dataHoraInicio(transactionTimeBegin != null ? new Date(Long.parseLong(transactionTimeBegin)) : null)
                    .dataHoraFim(new Date())
                    //TODO: Capturar usuário do SpringSecurity quando existir camada de segurança
                    .usuario("DEFAULT")
                    .build();

            logTransacional.setTempoRespostaMs(calcularTempoResposaMilisegundos(logTransacional.getDataHoraInicio(), logTransacional.getDataHoraFim()));
            applicationEventPublisher.publishEvent(logTransacional);
        } catch (Exception e) {
            log.error(e);
        }
    }

    public Integer calcularTempoResposaMilisegundos(Date dataHoraInicio, Date dataHoraFim) {
        if (dataHoraInicio != null && dataHoraFim != null) {
            Long tempoResposta = dataHoraFim.getTime() - dataHoraInicio.getTime();
            return tempoResposta.intValue();
        }
        return null;
    }

}
