package br.com.yagovcb.rhitmohospedeapi.config.handler;

import br.com.yagovcb.rhitmohospedeapi.application.enums.APIExceptionCode;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.*;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.response.ErrorMessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Locale;

@ControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;

    @Autowired
    public ExceptionHandlerAdvice(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @ExceptionHandler({ BusinessException.class, ValidaSenhaException.class })
    public final ResponseEntity<Object> handlerBusinessException(APIException ex, Locale locale){
        ErrorMessageResponse build = makeErrorRespondeBuilder(ex, locale);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(build);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, Locale locale) {
        ErrorMessageResponse build = makeErrorRespondeBuilder(ex, locale);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(build);

    }

    @ExceptionHandler(InvalidJwtAuthenticationException.class)
    public final ResponseEntity<BusinessException> invalidJwtAuthenticationException(Exception ex, WebRequest request) {
        BusinessException exceptionResponse = new BusinessException(APIExceptionCode.INVALID_CREDENTIALS, ex.getMessage());
        return ResponseEntity.badRequest().body(exceptionResponse);
    }

    private ErrorMessageResponse makeErrorRespondeBuilder(APIException ex, Locale locale){
        String msgKey = makeMessageKey(ex.getExceptionCode());
        String message = messageSource.getMessage(msgKey, null, ex.getMessage(), locale);
        if(message == null) {
            msgKey = APIExceptionCode.UNKNOWN.getKey();
            message = messageSource.getMessage(msgKey, null, null, locale);
        }
        logger.error(ex.getMessage());
        return ErrorMessageResponse.builder().code(msgKey).message(message).constraints(null).build();
    }

    private String makeMessageKey(APIExceptionCode exceptionCode) {
        return exceptionCode != null ? exceptionCode.getKey() : APIExceptionCode.UNKNOWN.getKey();
    }
}
