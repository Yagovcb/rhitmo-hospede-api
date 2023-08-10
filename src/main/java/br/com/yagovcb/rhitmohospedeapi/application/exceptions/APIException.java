package br.com.yagovcb.rhitmohospedeapi.application.exceptions;

import br.com.yagovcb.rhitmohospedeapi.application.enums.APIExceptionCode;
import lombok.Getter;

@Getter
public abstract class APIException extends RuntimeException {


    private APIExceptionCode exceptionCode;

    protected APIException(APIExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }
    protected APIException(APIExceptionCode exceptionCode, Throwable cause,String message) {
        super(message, cause);
        this.exceptionCode = exceptionCode;
    }
}
