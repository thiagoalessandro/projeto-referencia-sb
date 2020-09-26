package br.com.packagebase.projetoreferenciasb.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.MDC;

@Log4j2
public class TraceUtils {

    public static final String CONTEXT_API = "api";
    private static final String TRACE_ID = "traceId";
    private static final String IP = "ip";
    private static final String KEY_CONTEXTO = "keyContext";

    public static String generateTraceId() {
        return RandomStringUtils.randomAlphanumeric(15).toUpperCase();
    }

    public static void addTraceIdContextMDC(String context) {
        try {
            MDC.put(TraceUtils.TRACE_ID, String.format("[%s]", generateTraceId()));
            if (Strings.isNotEmpty(context)) {
                MDC.put(TraceUtils.KEY_CONTEXTO, String.format("[%s]", context));
            }
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

    public static String getKeyContexto() {
        try {
            if (MDC.get(TraceUtils.KEY_CONTEXTO) != null)
                return MDC.get(KEY_CONTEXTO);
        } catch (Exception e) {
            log.warn(" Erro ao recuperar contexto do MDC", e);
        }
        return null;
    }
    public static String getIp() {
        try {
            if (MDC.get(TraceUtils.IP) != null)
                return MDC.get(IP);
        } catch (Exception e) {
            log.warn(" Erro ao recuperar ip do MDC", e);
        }
        return null;
    }
}
