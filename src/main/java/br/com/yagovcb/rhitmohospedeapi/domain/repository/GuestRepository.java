package br.com.yagovcb.rhitmohospedeapi.domain.repository;

import br.com.yagovcb.rhitmohospedeapi.domain.model.Guest;
import br.com.yagovcb.rhitmohospedeapi.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

    Optional<Guest> findByEmailAndUser(String email, User user);

    @Query(value = "select g from Guest g join g.reservations r where g.email = :email and g.user = :user")
    Optional<Guest> findGuestByEmailAndUser(String email, User user);

    Optional<Guest> findByEmail(String email);

    Optional<Guest> findByUser(User user);
}
