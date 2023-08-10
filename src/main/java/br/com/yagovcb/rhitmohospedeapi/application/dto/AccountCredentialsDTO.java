package br.com.yagovcb.rhitmohospedeapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCredentialsDTO {
    private String username;

    private String password;
}
