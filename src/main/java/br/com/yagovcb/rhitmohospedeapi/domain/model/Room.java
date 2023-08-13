package br.com.yagovcb.rhitmohospedeapi.domain.model;

import br.com.yagovcb.rhitmohospedeapi.domain.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Builder
@Table(name = "room", schema = "management")
@AllArgsConstructor
@NoArgsConstructor
public class Room implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Size(min = 1, max = 8)
    @Column(name = "room_number", length = 8, nullable = false, unique = true)
    private int roomNumber;

    @Size(min = 1, max = 5)
    @Column(name = "number_guests", length = 5, nullable = false)
    private int numberGuests;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotEmpty
    @Column(name = "daily_value")
    private Double dailyValue;
}
