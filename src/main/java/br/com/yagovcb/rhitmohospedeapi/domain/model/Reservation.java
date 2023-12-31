package br.com.yagovcb.rhitmohospedeapi.domain.model;

import br.com.yagovcb.rhitmohospedeapi.domain.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Builder
@Table(name = "reservation", schema = "management")
@AllArgsConstructor
@NoArgsConstructor
public class Reservation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "code", length = 8, nullable = false)
    private String code;

    @Column(name = "reservation_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate reservationDate;

    @FutureOrPresent
    @Column(name = "checkin_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataCheckin;

    @Future
    @Column(name = "checkout_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataCheckout;

    @Column(name = "total_value")
    private Double totalValue;

    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    private Room room;

    @Column(name = "number_room_reserved")
    private int numberRoomReserved;

    @Column(name = "number_days_reserved")
    private int numberDaysReserved;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    private Guest guest;
}
