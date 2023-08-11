package br.com.yagovcb.rhitmohospedeapi.application.services;

import br.com.yagovcb.rhitmohospedeapi.application.dto.GuestDTO;
import br.com.yagovcb.rhitmohospedeapi.application.enums.APIExceptionCode;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.UserNotFoundException;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Guest;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.GuestRepository;
import br.com.yagovcb.rhitmohospedeapi.utils.GuestUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;

    public ResponseEntity<List<GuestDTO>> getAllGuest() {
        List<Guest> guestList = guestRepository.findAll();
        if (guestList.isEmpty()){
            throw new UserNotFoundException(APIExceptionCode.RESOURCE_NOT_FOUND, "Não foi encontrado nenhum hospede na Base");
        }
        return ResponseEntity.ok(GuestUtils.makeListGuestDTO(guestList));
    }
}
