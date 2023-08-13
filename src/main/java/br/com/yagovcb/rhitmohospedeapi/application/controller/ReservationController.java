package br.com.yagovcb.rhitmohospedeapi.application.controller;

import br.com.yagovcb.rhitmohospedeapi.application.dto.ReservationDTO;
import br.com.yagovcb.rhitmohospedeapi.application.services.ReservationService;
import br.com.yagovcb.rhitmohospedeapi.domain.enums.Status;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.CreateReservationRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.PaymentRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReservationController {

    private final ReservationService reservationService;


    @GetMapping(value = "/{status}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReservationDTO>> getAllReservationsByStatus(@PathVariable(name = "status") Status status) {
        return reservationService.getAllReservationsByStatus(status);
    }

    @GetMapping(value = "/reservations", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ReservationDTO>> getAllReservationsByData(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal) {
        return reservationService.getAllReservationsByData(dataInicial, dataFinal);
    }

    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponse> createReservation(@RequestBody CreateReservationRequest createReservationRequest){
        return reservationService.createReservation(createReservationRequest);
    }

    @PatchMapping(value = "/pay", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationResponse> reservationPayment(@RequestBody PaymentRequest paymentRequest){
        return reservationService.reservationPayment(paymentRequest);
    }

    @DeleteMapping(value = "/{code}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteReservation(@PathVariable(name = "code") String code){
        return reservationService.deleteReservation(code);
    }

}
