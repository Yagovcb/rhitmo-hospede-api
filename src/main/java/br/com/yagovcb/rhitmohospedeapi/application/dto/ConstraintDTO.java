package br.com.yagovcb.rhitmohospedeapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ConstraintDTO {
    private String objectName;
    private String fieldName;
    private String validationMessage;
}
