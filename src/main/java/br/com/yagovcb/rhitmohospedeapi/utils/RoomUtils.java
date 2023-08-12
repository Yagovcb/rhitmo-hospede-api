package br.com.yagovcb.rhitmohospedeapi.utils;

import br.com.yagovcb.rhitmohospedeapi.application.dto.RoomDTO;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Room;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RoomUtils {
    public static List<RoomDTO> makeListRoomDTO(List<Room> roomList) {
        List<RoomDTO> dtoList = new ArrayList<>();
        roomList.forEach(room -> dtoList.add(RoomDTO.builder()
                        .number(room.getNumber())
                        .numberGuests(room.getNumberGuests())
                        .description(room.getDescription())
                        .dailyValue(room.getDailyValue())
                .build()));
        return dtoList;
    }
}
