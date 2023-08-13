package br.com.yagovcb.rhitmohospedeapi.utils;

import br.com.yagovcb.rhitmohospedeapi.application.dto.RoomDTO;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Room;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.response.RoomResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomUtils {
    public static List<RoomDTO> makeListRoomDTO(List<Room> roomList) {
        List<RoomDTO> dtoList = new ArrayList<>();
        roomList.forEach(room -> dtoList.add(RoomDTO.builder()
                        .roomNumber(room.getRoomNumber())
                        .numberGuests(room.getNumberGuests())
                        .description(room.getDescription())
                        .dailyValue(room.getDailyValue())
                .build()));
        return dtoList;
    }

    public static RoomResponse makeRoomResponse(Room room){
        return RoomResponse.builder()
                .roomNumber(room.getRoomNumber())
                .numberGuests(room.getNumberGuests())
                .description(room.getDescription())
                .status(room.getStatus())
                .dailyValue(room.getDailyValue())
                .build();
    }
}
