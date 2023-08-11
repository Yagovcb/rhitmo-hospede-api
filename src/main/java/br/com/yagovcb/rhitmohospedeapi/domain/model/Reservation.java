package br.com.yagovcb.rhitmohospedeapi.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
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

    @Column(name = "checkin_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataCheckin;

    @Column(name = "checkout_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate dataCheckout;

    @Column(name = "total_value")
    private Double totalValue;

    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    private Room room;
}
