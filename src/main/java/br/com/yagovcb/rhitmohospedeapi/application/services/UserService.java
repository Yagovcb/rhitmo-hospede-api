package br.com.yagovcb.rhitmohospedeapi.application.services;

import br.com.yagovcb.rhitmohospedeapi.application.dto.UserDTO;
import br.com.yagovcb.rhitmohospedeapi.application.enums.APIExceptionCode;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.EmailException;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.UserNotFoundException;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.ValidaSenhaException;
import br.com.yagovcb.rhitmohospedeapi.domain.model.User;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.UserRepository;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.AddRoleInUserRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.RecoverPasswordRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.RegistrationUserRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.UserInformationRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.response.UserResponse;
import br.com.yagovcb.rhitmohospedeapi.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<List<UserDTO>> getAllUsers(Boolean ativo) {
        Optional<List<User>> optionalUserList = userRepository.findAllByActive(ativo);
        if (optionalUserList.isPresent()){
            return ResponseEntity.ok(UserUtils.makeListUserDTO(optionalUserList.get()));
        } else {
            throw new UserNotFoundException(APIExceptionCode.RESOURCE_NOT_FOUND, "Não foi encontrado nenhum usuario na Base");
        }
    }

    public ResponseEntity<UserResponse> findUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findByUsernameAndActive(email, Boolean.TRUE);
        if (optionalUser.isPresent()){
            return ResponseEntity.ok(UserUtils.makeUserResponse(optionalUser.get()));
        } else {
            throw new UserNotFoundException(APIExceptionCode.RESOURCE_NOT_FOUND, "Não foi encontrado nenhum usuario na Base");
        }
    }

    public ResponseEntity<UserResponse> updateUserInformation(UserInformationRequest informationRequest) {
        Optional<User> optionalUser = userRepository.findByUsernameAndActive(informationRequest.getUsername(), Boolean.TRUE);
        if (optionalUser.isPresent()){
            var user = optionalUser.get();
            user.setActive(informationRequest.isActive());
            return ResponseEntity.ok(UserUtils.makeUserResponse(userRepository.save(user)));
        } else {
            throw new UserNotFoundException(APIExceptionCode.RESOURCE_NOT_FOUND, "Não foi encontrado nenhum usuario na Base");
        }
    }

    public ResponseEntity<HttpStatus> updateUserInformationAddRole(AddRoleInUserRequest addRoleInUserRequest, String email) {
        Optional<User> optionalUser = userRepository.findByUsernameAndActive(email, Boolean.TRUE);
        if (optionalUser.isPresent()){
            var user = optionalUser.get();
            user.getRoles().add(addRoleInUserRequest.getRole());
            userRepository.save(user);
            return ResponseEntity.noContent().build();
        } else {
            throw new UserNotFoundException(APIExceptionCode.RESOURCE_NOT_FOUND, "Não foi encontrado nenhum usuario na Base");
        }
    }

    public ResponseEntity<HttpStatus> passwordRecover(RecoverPasswordRequest recoverPasswordRequest) throws ValidaSenhaException {
        Optional<User> optionalUser = userRepository.findByUsernameAndActive(recoverPasswordRequest.getUsername(), Boolean.TRUE);
        if (optionalUser.isPresent()){

            UserUtils.validaSenha(recoverPasswordRequest.getPassword(), recoverPasswordRequest.getReTypePassword());

            var user = optionalUser.get();

            if (passwordEncoder.matches(recoverPasswordRequest.getPassword(), user.getPassword())){
                throw new ValidaSenhaException(APIExceptionCode.PASSWORD_VALIDATION, "Você está tentando persistir uma senha igual a senha ja cadastrada!");
            }
            user.setPassword(passwordEncoder.encode(recoverPasswordRequest.getPassword()));
            userRepository.save(user);
            return ResponseEntity.noContent().build();
        } else {
            throw new UserNotFoundException(APIExceptionCode.RESOURCE_NOT_FOUND, "Não foi encontrado nenhum usuario na Base");
        }
    }

    public ResponseEntity<UserResponse> registrationUser(RegistrationUserRequest registrationUserRequest) throws ValidaSenhaException, EmailException {
        Optional<User> optionalUser = userRepository.findByUsernameAndActive(registrationUserRequest.getUsername(), Boolean.TRUE);
        log.debug("UserService :: não existe usuario previamente cadastrado");
        if (optionalUser.isEmpty()){
            UserUtils.validaSenha(registrationUserRequest.getPassword(), registrationUserRequest.getReTypePassword());
            UserUtils.validaEmail(registrationUserRequest.getUsername());
            log.debug("UserService :: seguindo para o processo de persistencia");
            User usuario = User.builder()
                    .username(registrationUserRequest.getUsername())
                    .password(passwordEncoder.encode(registrationUserRequest.getPassword()))
                    .recordDate(LocalDate.now())
                    .accountNonExpired(true)
                    .accountNonLocked(true)
                    .credentialsNonExpired(true)
                    .active(true)
                    .roles(Set.of(registrationUserRequest.getRole()))
                    .tokens(new ArrayList<>())
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(UserUtils.makeUserResponse(userRepository.save(usuario)));
        } else {
            throw new UserNotFoundException(APIExceptionCode.RESOURCE_NOT_FOUND,"Não foi encontrado nenhum usuario na Base");
        }
    }

    public ResponseEntity<HttpStatus> deleteUser(String email) {
        Optional<User> optionalUser = userRepository.findByUsernameAndActive(email, Boolean.TRUE);
        if (optionalUser.isPresent()){
            userRepository.delete(optionalUser.get());
            return ResponseEntity.noContent().build();
        } else {
            throw new UserNotFoundException(APIExceptionCode.RESOURCE_NOT_FOUND, "Não foi encontrado nenhum usuario na Base");
        }
    }
}
