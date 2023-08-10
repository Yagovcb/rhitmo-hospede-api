package br.com.yagovcb.rhitmohospedeapi.utils;

import br.com.yagovcb.rhitmohospedeapi.application.dto.UserDTO;
import br.com.yagovcb.rhitmohospedeapi.application.enums.APIExceptionCode;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.EmailException;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.ValidaSenhaException;
import br.com.yagovcb.rhitmohospedeapi.application.policies.PoliticaValidacaoEmail;
import br.com.yagovcb.rhitmohospedeapi.application.policies.PoliticaValidacaoSenha;
import br.com.yagovcb.rhitmohospedeapi.domain.model.User;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.response.UserResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtils {
    public static List<UserDTO> makeListUserDTO(List<User> users) {
        List<UserDTO> userDTOList = new ArrayList<>();

        users.forEach(user -> userDTOList.add(UserDTO.builder()
                        .userId(user.getId())
                        .dataCadastro(user.getRecordDate())
                        .email(user.getUsername())
                        .roles(user.getRoles())
                .build()));

        return userDTOList;
    }

    public static UserResponse makeUserResponse(User user) {
        return UserResponse.builder()
                .email(user.getUsername())
                .dataCadastro(user.getRecordDate())
                .roles(user.getRoles())
                .build();
    }

    public static void validaSenha(String senha, String senhaReescrita) throws ValidaSenhaException {
        if (Boolean.FALSE.equals(PoliticaValidacaoSenha.validaSenhaIgual(senha, senhaReescrita))) {
            throw new ValidaSenhaException(APIExceptionCode.PASSWORD_VALIDATION, "As senhas não conferem!");
        }
        if (Boolean.FALSE.equals(PoliticaValidacaoSenha.validaForcaSenha(senha))) {
            throw new ValidaSenhaException(APIExceptionCode.PASSWORD_VALIDATION, "As senhas não segue o padrão de segurança");
        }
    }

    public static void validaEmail(String email) throws EmailException {
        if (Boolean.FALSE.equals(PoliticaValidacaoEmail.validaEmail(email))) {
            throw new EmailException(APIExceptionCode.NON_STANDARD_EMAIL, "O email passado não segue o padrão.");
        }
    }
}
