package br.com.yagovcb.rhitmohospedeapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TokenDTO {
    private String username;
    private LocalDate created;
    private String accessToken;
    private String refreshToken;
}
