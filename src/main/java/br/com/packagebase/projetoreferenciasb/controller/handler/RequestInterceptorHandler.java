package br.com.packagebase.projetoreferenciasb.controller.handler;

import br.com.packagebase.projetoreferenciasb.utils.TraceUtils;
import io.micrometer.core.instrument.util.IOUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import static br.com.packagebase.projetoreferenciasb.utils.TraceUtils.CONTEXT_API;

@Log4j2
public class RequestInterceptorHandler implements HandlerInterceptor {

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
        TraceUtils.limparContextoMDC();
    }


}
