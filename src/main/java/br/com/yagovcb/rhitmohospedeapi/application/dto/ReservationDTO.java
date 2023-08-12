package br.com.yagovcb.rhitmohospedeapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationDTO {
    private String code;
    private LocalDate dataCheckin;
    private LocalDate dataCheckout;
    private Double totalValue;
    private int roomNumber;
}
