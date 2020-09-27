package br.com.packagebase.projetoreferenciasb.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.MDC;

import java.util.Date;

@Log4j2
public class TraceUtils {

    public static final String CONTEXT_API = "api";
    private static final String TRACE_ID = "traceId";
    private static final String IP = "ip";
    private static final String KEY_CONTEXTO = "keyContext";

    public static final String TRANSACTION_RESOURCE = "transactionResource";
    public static final String TRANSACTION_OPERATION = "transactionOperation";
    public static final String TRANSACTION_RESPONSE_ERRORS = "transactionErros";
    public static final String TRANSACTION_ID = "transactionId";
    public static final String TRANSACTION_EXCEPTION = "transactionException";
    public static final String TRANSACTION_TIME_BEGIN = "transactionTimeBegin";

    public static String generateTraceId() {
        return RandomStringUtils.randomAlphanumeric(15).toUpperCase();
    }

    public static void addTraceIdContextMDC(String context) {
        try {
            MDC.put(TraceUtils.TRACE_ID, String.format("[%s]", generateTraceId()));
            if (Strings.isNotEmpty(context)) {
                MDC.put(TraceUtils.KEY_CONTEXTO, String.format("[%s]", context));
            }
            MDC.put(TraceUtils.TRANSACTION_TIME_BEGIN, String.valueOf(new Date().getTime()));
        } catch (Exception e) {
            log.warn("Erro ao adicionar traceId e contexto ao MDC", e);
        }
    }

    public static void addContextMDC(String contexto) {
        try {
            MDC.put(TraceUtils.KEY_CONTEXTO, String.format("[%s]", contexto));
        } catch (Exception e) {
            log.warn("Erro ao adicionar contexto ao MDC", e);
        }
    }

    public static void limparContextoMDC() {
        try {
            MDC.clear();
        } catch (Exception e) {
            log.warn(" Erro ao limpar o MDC", e);
        }
    }

    public static String getTraceId() {
        try {
            if (MDC.get(TraceUtils.TRACE_ID) != null)
                return MDC.get(TRACE_ID).replace("[", "").replace("]", "");
        } catch (Exception e) {
            log.warn(" Erro ao recuperar traceId do MDC", e);
        }
        return null;
    }

    public static void setTransaction(String transaction, String value) {
        try {
            MDC.put(transaction, value);
        } catch (Exception e) {
            log.warn("Erro ao adicionar {} ao MDC", transaction, e);
        }
    }

    public static String getTransaction(String transaction) {
        try {
            if (MDC.get(transaction) != null)
                return MDC.get(transaction);
        } catch (Exception e) {
            log.warn(" Erro ao recuperar {} do MDC", transaction, e);
        }
        return null;
    }
}
