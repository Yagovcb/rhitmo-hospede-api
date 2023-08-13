package br.com.yagovcb.rhitmohospedeapi.application.services;

import br.com.yagovcb.rhitmohospedeapi.application.dto.RoomDTO;
import br.com.yagovcb.rhitmohospedeapi.application.enums.APIExceptionCode;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.BusinessException;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.IntegrationException;
import br.com.yagovcb.rhitmohospedeapi.domain.enums.Status;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Reservation;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Room;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.ReservationRepository;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.RoomRepository;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.CreateRoomRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.UpdateRoomRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.response.RoomResponse;
import br.com.yagovcb.rhitmohospedeapi.utils.RoomUtils;
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
public class RoomService {

    private final RoomRepository roomRepository;
    private final ReservationRepository reservationRepository;

    public ResponseEntity<List<RoomDTO>> findAllRoomByStatus(Status status, LocalDate dataInicial, LocalDate dataFinal) {
        List<Room> roomList = reservationRepository.findAllRoomByStatus(status, dataInicial, dataFinal);
        if (roomList.isEmpty()){
            throw new BusinessException(APIExceptionCode.RESOURCE_NOT_FOUND, "Nenhum quarto encontrado com esses parametros");
        }
        return ResponseEntity.ok(RoomUtils.makeListRoomDTO(roomList));
    }

    public ResponseEntity<RoomResponse> createRoom(CreateRoomRequest createRoomRequest) {
        Optional<Room> roomOptional = roomRepository.findByRoomNumber(createRoomRequest.getRoomNumber());
        if (roomOptional.isPresent()){
            throw new BusinessException(APIExceptionCode.RESOURCE_ALREADY_EXISTS, "Esse quarto já está cadastrado!");
        } else {
            Room r = new Room();
            r.setRoomNumber(createRoomRequest.getRoomNumber());
            r.setNumberGuests(createRoomRequest.getNumberGuests());
            r.setDescription(createRoomRequest.getDescription());
            r.setDailyValue(createRoomRequest.getDailyValue());
            r.setStatus(Status.DISPONIVEL);
            try {
                return ResponseEntity.status(HttpStatus.CREATED).body(RoomUtils.makeRoomResponse(roomRepository.save(r)));
            } catch (IntegrationException e){
                throw new IntegrationException(APIExceptionCode.UNKNOWN, "Ocorreu um erro desconhecido ao salvar o quarto!");
            }
        }
    }

    public ResponseEntity<HttpStatus> updateRoom(UpdateRoomRequest updateRoomRequest) {
        Optional<Room> roomOptional = roomRepository.findByRoomNumber(updateRoomRequest.getRoomNumber());
        if (roomOptional.isPresent()){
            Room room = roomOptional.get();
            room.setNumberGuests(updateRoomRequest.getNumberGuests());
            room.setDescription(updateRoomRequest.getDescription());
            room.setDailyValue(updateRoomRequest.getDailyValue());
            try {
                roomRepository.save(room);
                return ResponseEntity.noContent().build();
            } catch (IntegrationException e){
                throw new IntegrationException(APIExceptionCode.UNKNOWN, "Ocorreu um erro desconhecido ao salvar o quarto!");
            }

        } else {
            throw new BusinessException(APIExceptionCode.RESOURCE_NOT_FOUND, "Nenhum quarto encontrado com esses parametros");
        }
    }

    public ResponseEntity<HttpStatus> deleteRoom(int roomNumber) {
        Optional<List<Reservation>> optionalReservationList = reservationRepository.findAllByNumberRoomReserved(roomNumber);

        if (optionalReservationList.isPresent()){
            throw new IntegrationException(APIExceptionCode.UNKNOWN, "Você não pode excluir um quarto com uma reserva cadastrada");
        } else {
            Optional<Room> optionalRoom = roomRepository.findByRoomNumber(roomNumber);
            if (optionalRoom.isPresent()){
                try {
                    roomRepository.delete(optionalRoom.get());
                    return ResponseEntity.noContent().build();
                } catch (IntegrationException e){
                    throw new IntegrationException(APIExceptionCode.UNKNOWN, "Ocorreu um erro desconhecido ao deletar o quarto!");
                }
            } else {
                throw new BusinessException(APIExceptionCode.RESOURCE_NOT_FOUND, "Nenhum quarto encontrado com esses parametros");
            }
        }
    }
}
