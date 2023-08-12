package br.com.yagovcb.rhitmohospedeapi.application.services;

import br.com.yagovcb.rhitmohospedeapi.application.dto.GuestDTO;
import br.com.yagovcb.rhitmohospedeapi.application.enums.APIExceptionCode;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.BusinessException;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.IntegrationException;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.UserNotFoundException;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Guest;
import br.com.yagovcb.rhitmohospedeapi.domain.model.User;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.GuestRepository;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.UserRepository;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.GuestNumberRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.GuestReservationRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.RegistrationUserRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.response.GuestReservationResponse;
import br.com.yagovcb.rhitmohospedeapi.utils.GuestUtils;
import br.com.yagovcb.rhitmohospedeapi.utils.ReservationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final UserRepository userRepository;

    public ResponseEntity<List<GuestDTO>> getAllGuest() {
        List<Guest> guestList = guestRepository.findAll();
        if (guestList.isEmpty()){
            throw new UserNotFoundException(APIExceptionCode.RESOURCE_NOT_FOUND, "Não foi encontrado nenhum hospede na Base");
        }
        return ResponseEntity.ok(GuestUtils.makeListGuestDTO(guestList));
    }

    public ResponseEntity<GuestReservationResponse> getAllReservationByGuest(GuestReservationRequest guestReservationRequest) {
        Optional<User> userOptional = userRepository.findByUsername(guestReservationRequest.getUserName());
        if(userOptional.isPresent()){
            Optional<Guest> guestOptional = guestRepository.findGuestByEmailAndUser(guestReservationRequest.getEmail(), userOptional.get());
            if (guestOptional.isPresent()) {
                var guest = guestOptional.get();
                GuestReservationResponse response = GuestReservationResponse.builder()
                        .nome(guest.getName())
                        .email(guest.getEmail())
                        .userName(guest.getUser().getUsername())
                        .reservationList(ReservationUtils.makeReservationListDTO(guest.getReservations()))
                        .build();
                return ResponseEntity.ok(response);
            } else {
                throw new UserNotFoundException(APIExceptionCode.RESOURCE_NOT_FOUND, "Não foi encontrado nenhum guest na Base");
            }
        } else {
            throw new UserNotFoundException(APIExceptionCode.RESOURCE_NOT_FOUND, "Não foi encontrado nenhum user na Base");
        }
    }

    public void createGuestByUser(User usuario, RegistrationUserRequest registrationUserRequest) {
        Optional<Guest> guestOptional = guestRepository.findByEmailAndUser(registrationUserRequest.getEmail(), usuario);
        if (guestOptional.isEmpty()){
            try {
                guestRepository.save(Guest.builder()
                        .user(usuario)
                        .email(registrationUserRequest.getEmail())
                        .reservations(new ArrayList<>())
                        .build());
            } catch (IntegrationException e){
                throw new IntegrationException(APIExceptionCode.UNKNOWN, "Ocorreu um erro desconhecido ao salvar o guest!");
            }
        } else {
            throw new BusinessException(APIExceptionCode.RESOURCE_ALREADY_EXISTS, "Guest já cadastrado");
        }
    }

    public ResponseEntity<HttpStatus> updateGuestNumber(GuestNumberRequest guestNumberRequest) {
        Optional<Guest> guestOptional = guestRepository.findByEmail(guestNumberRequest.getEmail());
        if (guestOptional.isPresent()){
            try {
                var guest = guestOptional.get();
                guest.setPhone(guestNumberRequest.getCellPhone());
                guestRepository.save(guest);
            } catch (IntegrationException e){
                throw new IntegrationException(APIExceptionCode.UNKNOWN, "Ocorreu um erro desconhecido ao salvar o guest!");
            }
        } else {
            throw new BusinessException(APIExceptionCode.RESOURCE_NOT_FOUND, "Guest não foi encontrado");
        }

        return ResponseEntity.noContent().build();
    }

}
