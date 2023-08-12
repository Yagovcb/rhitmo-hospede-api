package br.com.yagovcb.rhitmohospedeapi.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private int number;
    private int numberGuests;
    private String description;
    private Double dailyValue;
}
