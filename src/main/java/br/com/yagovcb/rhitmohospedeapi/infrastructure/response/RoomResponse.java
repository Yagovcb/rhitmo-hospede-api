package br.com.yagovcb.rhitmohospedeapi.infrastructure.response;

import br.com.yagovcb.rhitmohospedeapi.domain.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomResponse {
    private int roomNumber;
    private int numberGuests;
    private Status status;
    private String description;
    private Double dailyValue;
}
