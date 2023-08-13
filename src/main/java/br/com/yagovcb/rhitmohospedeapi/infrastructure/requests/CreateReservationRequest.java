package br.com.yagovcb.rhitmohospedeapi.infrastructure.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateReservationRequest {
    private int numberRoom;
    @FutureOrPresent
    private LocalDate reservationDate;
    @NotBlank
    private String username;
    private Long numberDaysReserved;
}
