package br.com.yagovcb.rhitmohospedeapi.application.exceptions;

import br.com.yagovcb.rhitmohospedeapi.application.enums.APIExceptionCode;

public class IntegrationException extends APIException {

    public IntegrationException(APIExceptionCode exceptionCode, String message) {
        super(exceptionCode, message);
    }

    public IntegrationException(APIExceptionCode exceptionCode, Throwable cause, String message) {
        super(exceptionCode, cause, message);
    }

    @Override
    public APIExceptionCode getExceptionCode() {
        return super.getExceptionCode();
    }
}
