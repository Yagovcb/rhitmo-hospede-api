package br.com.yagovcb.rhitmohospedeapi.infrastructure.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreateRoomRequest {

    @Size(min = 1, max = 8)
    private int roomNumber;
    @Size(min = 1, max = 5)
    private int numberGuests;

    @NotBlank
    private String description;

    @NotEmpty
    private Double dailyValue;
}
