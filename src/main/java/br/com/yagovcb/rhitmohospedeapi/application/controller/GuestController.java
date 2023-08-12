package br.com.yagovcb.rhitmohospedeapi.application.controller;

import br.com.yagovcb.rhitmohospedeapi.application.dto.GuestDTO;
import br.com.yagovcb.rhitmohospedeapi.application.services.GuestService;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.GuestNumberRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.GuestReservationRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.response.GuestReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/guest")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class GuestController {

    private final GuestService guestService;

    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GuestDTO>> getAllGuest(){
        log.info("GuestController :: Recuperando todos os hospedes cadastrados...");
        return guestService.getAllGuest();
    }

    @GetMapping(value = "/reservations", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GuestReservationResponse> getAllReservationByGuest(GuestReservationRequest guestReservationRequest){
        log.info("GuestController :: Recuperando todas as reservas do guest...");
        return guestService.getAllReservationByGuest(guestReservationRequest);
    }

    @PatchMapping(value = "/guestNumber", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> updateGuestNumber(@RequestBody GuestNumberRequest guestNumberRequest){
        log.info("GuestController :: Atualizando hospede previamente cadastrado cadastrados...");
        return guestService.updateGuestNumber(guestNumberRequest);
    }
}
