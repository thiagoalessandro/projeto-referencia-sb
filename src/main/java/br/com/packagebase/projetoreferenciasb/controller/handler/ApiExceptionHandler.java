package br.com.packagebase.projetoreferenciasb.controller.handler;

import br.com.packagebase.projetoreferenciasb.component.MessagesApp;
import br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.response.Response;
import br.com.packagebase.projetoreferenciasb.exception.ValidationException;
import br.com.packagebase.projetoreferenciasb.utils.TraceUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessagesApp messagesApp;

    public ApiExceptionHandler(MessagesApp messagesApp) {
        this.messagesApp = messagesApp;
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response<List<String>> response = new Response<>();
        response.getErrors().add(ex.getCause().getMessage());
        return super.handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status,
                                                                  WebRequest request) {
        Response<List<String>> response = new Response<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            response.getErrors().add(messagesApp.handleMessage(Objects.requireNonNull(fieldError.getDefaultMessage())));
        }
        registerErrorsMDC(response.getErrors());
        return super.handleExceptionInternal(ex, response, headers, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> constraintViolationException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        Response<List<String>> response = new Response<>();
        for (ConstraintViolation<?> violation : violations) {
            response.getErrors().add(violation.getMessage());
        }
        registerErrorsMDC(response.getErrors());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = {ValidationException.class})
    public ResponseEntity<Object> validationException(ValidationException e) {
        Response<List<String>> response = new Response<>();
        String error = messagesApp.handleMessage(e.getMessage());
        response.getErrors().add(error);
        registerErrorsMDC(response.getErrors());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity illegalArgumentException(IllegalArgumentException exception) {
        Response<List<String>> response = new Response<>();
        String error = messagesApp.handleMessage(exception.getMessage());
        response.getErrors().add(error);
        registerErrorsMDC(response.getErrors());
        return ResponseEntity.badRequest().body(response);
    }

    private void registerErrorsMDC(List<String> errors) {
        TraceUtils.setTransaction(TraceUtils.TRANSACTION_RESPONSE_ERRORS, ArrayUtils.toString(errors));
    }

}
