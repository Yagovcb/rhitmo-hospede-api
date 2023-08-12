package br.com.yagovcb.rhitmohospedeapi.application.services;

import br.com.yagovcb.rhitmohospedeapi.application.dto.RoomDTO;
import br.com.yagovcb.rhitmohospedeapi.application.enums.APIExceptionCode;
import br.com.yagovcb.rhitmohospedeapi.application.exceptions.BusinessException;
import br.com.yagovcb.rhitmohospedeapi.domain.enums.Status;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Room;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.ReservationRepository;
import br.com.yagovcb.rhitmohospedeapi.domain.repository.RoomRepository;
import br.com.yagovcb.rhitmohospedeapi.utils.RoomUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

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
}
