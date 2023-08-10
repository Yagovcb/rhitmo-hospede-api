package br.com.yagovcb.rhitmohospedeapi.infrastructure.response;

import br.com.yagovcb.rhitmohospedeapi.application.dto.ConstraintDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorMessageResponse {
    private String code;
    private String message;
    private List<ConstraintDTO> constraints;
}
