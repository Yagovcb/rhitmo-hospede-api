package br.com.yagovcb.rhitmohospedeapi.utils;

import br.com.yagovcb.rhitmohospedeapi.application.dto.ReservationDTO;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Reservation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ReservationUtils {

    public static List<ReservationDTO> makeReservationListDTO(List<Reservation> reservations) {
        List<ReservationDTO> dtoList = new ArrayList<>();

        reservations.forEach(reservation -> dtoList.add(ReservationDTO.builder()
                        .code(reservation.getCode())
                        .dataCheckin(reservation.getDataCheckin())
                        .dataCheckout(reservation.getDataCheckout())
                        .totalValue(reservation.getTotalValue())
                        .roomNumber(reservation.getRoom().getNumber())
                .build()));

        return dtoList;
    }
}
