package br.com.yagovcb.rhitmohospedeapi.domain.model;

import br.com.yagovcb.rhitmohospedeapi.domain.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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

    @Column(name = "number", length = 8, nullable = false)
    private int number;

    @Column(name = "number_guests", length = 8, nullable = false)
    private int numberGuests;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotBlank
    @Column(nullable = false)
    private String description;

    @Column(name = "daily_value")
    private Double dailyValue;
}
