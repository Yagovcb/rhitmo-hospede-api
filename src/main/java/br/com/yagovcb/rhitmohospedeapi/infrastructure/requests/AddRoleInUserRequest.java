package br.com.yagovcb.rhitmohospedeapi.infrastructure.requests;

import br.com.yagovcb.rhitmohospedeapi.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddRoleInUserRequest {
    private Role role;
}
