package br.com.yagovcb.rhitmohospedeapi.infrastructure.response;

import br.com.yagovcb.rhitmohospedeapi.application.dto.ReservationDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GuestReservationResponse {
    private String nome;
    private String userName;
    private String email;
    private List<ReservationDTO> reservationList;
}
