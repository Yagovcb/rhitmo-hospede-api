package br.com.yagovcb.rhitmohospedeapi.application.controller;

import br.com.yagovcb.rhitmohospedeapi.application.dto.UserDTO;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.EmailException;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.ValidaSenhaException;
import br.com.yagovcb.rhitmohospedeapi.application.services.UserService;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.AddRoleInUserRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.RecoverPasswordRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.RegistrationUserRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.UserInformationRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.response.UserResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/actives/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllActiveUsers(){
        log.info("UserController :: Recuperando todos os usuarios ativos cadastrados...");
        return userService.getAllUsers(Boolean.TRUE);
    }

    @GetMapping(value = "/inactives/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDTO>> getAllInactiveUsers(){
        log.info("UserController :: Recuperando todos os usuarios inativos cadastrados...");
        return userService.getAllUsers(Boolean.FALSE);
    }

    @GetMapping(value = "/{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> getUserByUsername(@PathVariable(name = "username") String username){
        log.info("UserController :: Recuperando usuario por email cadastrado...");
        return userService.findUserByUsername(username);
    }

    @PatchMapping(value = "/updateInfo", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> updateUserInformation(@RequestBody UserInformationRequest informationRequest){
        log.info("UserController :: Atualizando informações do usuario...");
        return userService.updateUserInformation(informationRequest);
    }


    @PatchMapping(value = "/updateInfo/{username}/role", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> updateUserInformationAddRole(@RequestBody AddRoleInUserRequest addRoleInUserRequest, @PathVariable(name = "username") String username){
        log.info("UserController :: Adicionando nova Role para usuario...");
        return userService.updateUserInformationAddRole(addRoleInUserRequest, username);
    }

    @PostMapping(value = "/password-recover", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> passwordRecover(@RequestBody RecoverPasswordRequest recoverPasswordRequest) throws ValidaSenhaException {
        log.info("UserController :: Alterando senha do Usuario...");
        return userService.passwordRecover(recoverPasswordRequest);
    }


    @PostMapping(value = "/", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResponse> registrationUser(@RequestBody RegistrationUserRequest registrationUserRequest) throws ValidaSenhaException, EmailException {
        log.info("UserController :: Iniciando o processo de persistencia de novo usuario...");
        return userService.registrationUser(registrationUserRequest);
    }

    @DeleteMapping(value = "/{username}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable(name = "username") String username) {
        log.info("UserController :: Deletando usuario...");
        return userService.deleteUser(username);
    }
}
