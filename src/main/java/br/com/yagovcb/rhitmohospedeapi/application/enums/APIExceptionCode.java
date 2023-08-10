package br.com.yagovcb.rhitmohospedeapi.application.enums;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum APIExceptionCode {
    // Codes for generic exceptions
    UNKNOWN("GE-000"),
    UNAUTHENTICATED("GE-001"),

    // Codes for business exceptions
    RESOURCE_NOT_FOUND("BE-001"),
    RESOURCE_ALREADY_EXISTS("BE-002"),
    CPF_ALREADY_EXISTS("BE-003"),
    EMAIL_ALREADY_EXISTS("BE-004"),
    SEND_MAIL_ERROR("BE-005"),
    RESERVATION_NOT_FOUND("BE-006"),
    DEPENDENT_NOT_FOUND("BE-007"),
    CHECKIN_NOT_YET_DONE("BE-008"),
    CHECKIN_ALREADY_DONE("BE-009"),
    TOO_MANY_DEPENDENTS("BE-010"),
    CHECKOUT_ALREADY_DONE("BE-011"),
    RESERVATION_ALREADY_LOCALIZED("BE-012"),
    RESERVATION_OF_ANOTHER_GUEST("BE-013"),
    ERROR_GETTING_MAGICKEY_PASSWD("BE-014"),
    RESERVATION_NOT_LOCALIZED_YET("BE-015"),
    RESERVATION_OF_ANOTHER_ESTABLISHMENT("BE-016"),
    NON_STANDARD_EMAIL("BE-017"),

    // Codes validations
    CONSTRAINT_VALIDATION("V-001"),
    VIOLATION_REFERENCE("V-002"),
    PASSWORD_VALIDATION("V-003"),


    // Authentication
    INVALID_CREDENTIALS("AUTH-001"),
    USER_IS_NOT_ACTIVE("AUTH-002"),
    ACCESS_FORBIDDEN("AUTH-003");


    @Getter
    @NonNull
    private String key;
}
