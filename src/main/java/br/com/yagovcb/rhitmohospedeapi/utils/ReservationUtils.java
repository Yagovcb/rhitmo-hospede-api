package br.com.yagovcb.rhitmohospedeapi.utils;

import br.com.yagovcb.rhitmohospedeapi.application.dto.ReservationDTO;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Reservation;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Room;
import br.com.yagovcb.rhitmohospedeapi.infrastructure.response.ReservationResponse;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationUtils {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int CODE_LENGTH = 8;

    public static String gerarCodigo() {
        SecureRandom random = new SecureRandom();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            code.append(randomChar);
        }

        return code.toString();
    }

    public static List<ReservationDTO> makeReservationListDTO(List<Reservation> reservations) {
        List<ReservationDTO> dtoList = new ArrayList<>();

        reservations.forEach(reservation -> dtoList.add(ReservationDTO.builder()
                        .code(reservation.getCode())
                        .dataCheckin(reservation.getDataCheckin())
                        .dataCheckout(reservation.getDataCheckout())
                        .totalValue(reservation.getTotalValue())
                        .roomNumber(reservation.getRoom().getRoomNumber())
                .build()));

        return dtoList;
    }

    public static Double calculateTotalValue(Room room, Long numberDaysReserved) {
        return room.getDailyValue() * numberDaysReserved;
    }

    public static ReservationResponse makeReservationResponse(Reservation save) {
        return ReservationResponse.builder()
                .code(save.getCode())
                .dataCheckin(save.getDataCheckin())
                .dataCheckout(save.getDataCheckout())
                .totalValue(save.getTotalValue())
                .roomNumber(save.getRoom().getRoomNumber())
                .status(save.getStatus().name())
                .build();
    }
}
