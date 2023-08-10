package br.com.yagovcb.rhitmohospedeapi.utils;

import br.com.yagovcb.rhitmohospedeapi.application.dto.TokenDTO;
import br.com.yagovcb.rhitmohospedeapi.application.enums.APIExceptionCode;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenUtils {

    public static TokenDTO getTokenDTOBuilder(String accessToken, String refreshToken, String user){
        return TokenDTO.builder()
                .username(user)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .created(LocalDate.now())
                .build();
    }

    public static  String makeMessageKey(APIExceptionCode exceptionCode) {
        return exceptionCode != null ? exceptionCode.getKey() : APIExceptionCode.UNKNOWN.getKey();
    }
}
