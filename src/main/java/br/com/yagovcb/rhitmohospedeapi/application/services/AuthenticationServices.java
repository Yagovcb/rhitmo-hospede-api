package br.com.yagovcb.rhitmohospedeapi.application.services;

import br.com.yagovcb.rhitmohospedeapi.application.dto.AccountCredentialsDTO;
import br.com.yagovcb.rhitmohospedeapi.application.dto.TokenDTO;
import br.com.yagovcb.rhitmohospedeapi.application.enums.APIExceptionCode;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.InvalidJwtAuthenticationException;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.UserNotFoundException;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.ValidaSenhaException;
import br.com.yagovcb.rhitmohospedeapi.domain.enums.TokenType;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Token;
import br.com.yagovcb.rhitmohospedeapi.domain.model.User;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.TokenRepository;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.UserRepository;
import br.com.yagovcb.rhitmohospedeapi.utils.TokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationServices {

    private final AuthenticationManager authenticationManager;
    private final UserRepository usuarioRepository;
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    public ResponseEntity<TokenDTO> singIn(AccountCredentialsDTO accountCredentialsDTO) {
        try {
            var username = accountCredentialsDTO.getUsername();
            var password = accountCredentialsDTO.getPassword();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            Optional<User> usuarioCadastrado = usuarioRepository.findByUsername(username);
            if (usuarioCadastrado.isPresent()) {

                var jwtToken = jwtService.generateToken(usuarioCadastrado.get());
                var refreshToken = jwtService.generateRefreshToken(usuarioCadastrado.get());
                revokeAllUserTokens(usuarioCadastrado.get());
                saveUserToken(usuarioCadastrado.get(), jwtToken);
                return ResponseEntity.ok(TokenUtils.getTokenDTOBuilder(jwtToken, refreshToken, accountCredentialsDTO.getUsername()));
            } else {
                throw new UserNotFoundException(APIExceptionCode.RESOURCE_NOT_FOUND, "Username " + username + " não encontrado!");
            }
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new ValidaSenhaException(APIExceptionCode.PASSWORD_VALIDATION, "Usuario ou senha inválida");
        }
    }

    public ResponseEntity<TokenDTO> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidJwtAuthenticationException(APIExceptionCode.INVALID_CREDENTIALS, "Token não existe ou está incorreto");
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            var user = usuarioRepository.findByUsername(userEmail).orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                return ResponseEntity.ok(TokenUtils.getTokenDTOBuilder(accessToken, refreshToken, userEmail));
            }
        }
        return ResponseEntity.noContent().build();
    }

    private void revokeAllUserTokens(User user) {
        Optional<List<Token>> validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.get().forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens.get());
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

}
