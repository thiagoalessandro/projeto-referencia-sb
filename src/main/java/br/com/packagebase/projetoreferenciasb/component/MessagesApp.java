package br.com.packagebase.projetoreferenciasb.component;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessagesApp {

    private static final String PREFIX_MESSAGE_PROPERTY = "message.";

    private final ResourceBundleMessageSource messageSource;

    private static final Locale LOCALE_DEFAULT = new Locale("pt", "br");

    public MessagesApp(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String get(String code) {
        return messageSource.getMessage(code, new Object[]{}, getLocale());
    }

    public String get(String code, Object[] args) {
        return messageSource.getMessage(code, args, getLocale());
    }

    public String get(String code, String arg) {
        return messageSource.getMessage(code, new Object[]{arg}, getLocale());
    }

    public String handleMessage(String message){
        if(message.contains(PREFIX_MESSAGE_PROPERTY))
            return get(message);
        return message;
    }

    public Locale getLocale() {
        return LOCALE_DEFAULT;
    }

}
