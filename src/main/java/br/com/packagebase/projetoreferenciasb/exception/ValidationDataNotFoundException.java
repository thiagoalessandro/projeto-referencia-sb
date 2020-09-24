package br.com.packagebase.projetoreferenciasb.exception;

public class ValidationDataNotFoundException extends ValidationException {

    public ValidationDataNotFoundException() {
        super("message.repository.default.data.notfound");
    }
}
