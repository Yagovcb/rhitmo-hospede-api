package br.com.yagovcb.rhitmohospedeapi.application.services;

import br.com.yagovcb.rhitmohospedeapi.application.dto.ReservationDTO;
import br.com.yagovcb.rhitmohospedeapi.application.enums.APIExceptionCode;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.BusinessException;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.IntegrationException;
import br.com.yagovcb.rhitmohospedeapi.domain.enums.Status;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Guest;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Reservation;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Room;
import br.com.yagovcb.rhitmohospedeapi.domain.model.User;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.GuestRepository;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.ReservationRepository;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.RoomRepository;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.UserRepository;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.CreateReservationRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.PaymentRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.response.ReservationResponse;
import br.com.yagovcb.rhitmohospedeapi.utils.ReservationUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {
    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;
    private final GuestRepository guestRepository;
    private final UserRepository userRepository;

    public ResponseEntity<List<ReservationDTO>> getAllReservationsByStatus(Status status) {
        List<Reservation> reservationList = reservationRepository.findAllByStatus(status);
        if (reservationList.isEmpty()){
            throw new BusinessException(APIExceptionCode.RESOURCE_NOT_FOUND, "Nenhuma reserva encontrada com esses parametros");
        }

        return ResponseEntity.ok(ReservationUtils.makeReservationListDTO(reservationList));
    }

    public ResponseEntity<List<ReservationDTO>> getAllReservationsByData(LocalDate dataInicial, LocalDate dataFinal) {
        List<Reservation> reservationList = reservationRepository.findAllByReservationDateBetween(dataInicial, dataFinal);
        if (reservationList.isEmpty()){
            throw new BusinessException(APIExceptionCode.RESOURCE_NOT_FOUND, "Nenhuma reserva encontrada com esses parametros");
        }

        return ResponseEntity.ok(ReservationUtils.makeReservationListDTO(reservationList));
    }

    public ResponseEntity<ReservationResponse> createReservation(CreateReservationRequest createReservationRequest) {
        Optional<User> userOptional = userRepository.findByUsername(createReservationRequest.getUsername());
        if (userOptional.isEmpty()){
            throw new BusinessException(APIExceptionCode.RESOURCE_NOT_FOUND, "Nenhum user encontrado com esses parametros");
        }
        Optional<Guest> optionalGuest = guestRepository.findByUser(userOptional.get());
        if (optionalGuest.isEmpty()){
            throw new BusinessException(APIExceptionCode.RESOURCE_NOT_FOUND, "Nenhum guest encontrado com esses parametros");
        }
        Optional<Reservation> optionalReservation = reservationRepository.findByGuestAndReservationDate(optionalGuest.get(), createReservationRequest.getReservationDate());
        if (optionalReservation.isPresent()){
            throw new BusinessException(APIExceptionCode.RESOURCE_NOT_FOUND, "Você já possui uma reserva pra essa data!");
        }

        Optional<Reservation> reserved = reservationRepository.findByReservationDateAndNumberRoomReserved(createReservationRequest.getReservationDate(), createReservationRequest.getNumberRoom());
        if (reserved.isPresent()){
            throw new BusinessException(APIExceptionCode.RESOURCE_NOT_FOUND, "Já existe uma reserva pra essa data!");
        }

        Optional<Room> optionalRoom = roomRepository.findByRoomNumber(createReservationRequest.getNumberRoom());
        if (optionalRoom.isEmpty()){
            throw new BusinessException(APIExceptionCode.RESOURCE_NOT_FOUND, "Nenhum quarto encontrado com esses parametros");
        }

        var reservationCode = ReservationUtils.gerarCodigo();


        Reservation reservation = new Reservation();
        reservation.setCode(reservationRepository.existsByCode(reservationCode) ? ReservationUtils.gerarCodigo() : reservationCode);
        reservation.setReservationDate(createReservationRequest.getReservationDate());
        reservation.setDataCheckin(createReservationRequest.getReservationDate());
        reservation.setDataCheckout(createReservationRequest.getReservationDate().plusDays(createReservationRequest.getNumberDaysReserved()));
        reservation.setNumberRoomReserved(createReservationRequest.getNumberRoom());
        reservation.setStatus(Status.PRE_RESERVA);
        reservation.setGuest(optionalGuest.get());
        reservation.setTotalValue(ReservationUtils.calculateTotalValue(optionalRoom.get(), createReservationRequest.getNumberDaysReserved()));
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(ReservationUtils.makeReservationResponse(reservationRepository.save(reservation)));
        } catch (IntegrationException e){
            throw new IntegrationException(APIExceptionCode.UNKNOWN, "Ocorreu um erro desconhecido ao salvar o quarto!");
        }
    }

    public ResponseEntity<HttpStatus> deleteReservation(String code) {
        Optional<Reservation> optionalReservation = reservationRepository.findByCode(code);
        if (optionalReservation.isEmpty()){
            throw new BusinessException(APIExceptionCode.RESOURCE_NOT_FOUND, "Nenhuma reserva encontrada!");
        }
        try {
            reservationRepository.delete(optionalReservation.get());
            return ResponseEntity.noContent().build();
        } catch (IntegrationException e){
            throw new IntegrationException(APIExceptionCode.UNKNOWN, "Ocorreu um erro desconhecido ao deletar a reserva!");
        }
    }

    public ResponseEntity<ReservationResponse> reservationPayment(PaymentRequest paymentRequest) {
        Optional<Reservation> optionalReservation = reservationRepository.findByCode(paymentRequest.getCode());
        if (optionalReservation.isEmpty()){
            throw new BusinessException(APIExceptionCode.RESOURCE_NOT_FOUND, "Nenhuma reserva encontrada!");
        }
        try {
            Reservation reservation = optionalReservation.get();
            reservation.setStatus(Status.RESERVADO);
            return ResponseEntity.ok(ReservationUtils.makeReservationResponse(reservation));
        } catch (IntegrationException e){
            throw new IntegrationException(APIExceptionCode.UNKNOWN, "Ocorreu um erro desconhecido ao deletar a reserva!");
        }
    }
}
