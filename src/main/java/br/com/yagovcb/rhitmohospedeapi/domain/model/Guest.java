package br.com.yagovcb.rhitmohospedeapi.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.format.annotation.NumberFormat;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@Table(name = "guest", schema = "routine")
@AllArgsConstructor
@NoArgsConstructor
public class Guest implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotBlank
    @Size(min = 5, max = 120)
    @Column(nullable = false, unique = true, length = 120)
    private String email;

    @NumberFormat(style = NumberFormat.Style.NUMBER, pattern = "(99)99999-9999")
    private String phone;

    @OneToOne(orphanRemoval = true, fetch = FetchType.EAGER)
    private User user;

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Reservation> reservations;
}
