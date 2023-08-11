package br.com.yagovcb.rhitmohospedeapi.application.controller;

import br.com.yagovcb.rhitmohospedeapi.application.dto.GuestDTO;
import br.com.yagovcb.rhitmohospedeapi.application.services.GuestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
