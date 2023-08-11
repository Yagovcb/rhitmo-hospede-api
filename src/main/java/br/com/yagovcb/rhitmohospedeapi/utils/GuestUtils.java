package br.com.yagovcb.rhitmohospedeapi.utils;

import br.com.yagovcb.rhitmohospedeapi.application.dto.GuestDTO;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Guest;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GuestUtils {

    public static List<GuestDTO> makeListGuestDTO(List<Guest> guests){
        List<GuestDTO> guestDTOList = new ArrayList<>();

        guests.forEach(
                guest -> guestDTOList.add(
                    GuestDTO.builder()
                            .name(guest.getName())
                            .phone(guest.getPhone())
                            .email(guest.getEmail())
                            .build()
                ));

        return guestDTOList;
    }

}
