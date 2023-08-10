package br.com.yagovcb.rhitmohospedeapi.infrastructure.requests;

import br.com.yagovcb.rhitmohospedeapi.domain.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RegistrationUserRequest {

    @NotBlank
    @Size(min = 5, max = 30)
    private String password;

    @NotBlank
    @Size(min = 5, max = 30)
    private String reTypePassword;

    @NotBlank
    @Size(min = 5, max = 120)
    private String username;

    private Role role;
}
