package br.com.yagovcb.rhitmohospedeapi.application.controller;

import br.com.yagovcb.rhitmohospedeapi.application.dto.RoomDTO;
import br.com.yagovcb.rhitmohospedeapi.application.services.RoomService;
import br.com.yagovcb.rhitmohospedeapi.domain.enums.Status;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.CreateRoomRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.requests.UpdateRoomRequest;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.response.RoomResponse;
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
@RequestMapping("/room")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoomController {

    private final RoomService roomService;

    @GetMapping(value = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoomDTO>> findAllRoomByStatusActive(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal){
        return roomService.findAllRoomByStatus(Status.DISPONIVEL, dataInicial, dataFinal);
    }

    @GetMapping(value = "/inactive", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RoomDTO>> findAllRoomByStatusInactive(@RequestParam LocalDate dataInicial, @RequestParam LocalDate dataFinal){
        return roomService.findAllRoomByStatus(Status.RESERVADO, dataInicial, dataFinal);
    }
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RoomResponse> createRoom(@RequestBody CreateRoomRequest createRoomRequest){
        return roomService.createRoom(createRoomRequest);
    }

    @PatchMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> updateRoom(@RequestBody UpdateRoomRequest updateRoomRequest){
        return roomService.updateRoom(updateRoomRequest);
    }

    @DeleteMapping(value = "/{roomNumber}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> deleteRoom(@PathVariable(name = "roomNumber") int roomNumber){
        return roomService.deleteRoom(roomNumber);
    }
}
