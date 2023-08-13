package br.com.yagovcb.rhitmohospedeapi.domain.repository;

import br.com.yagovcb.rhitmohospedeapi.domain.enums.Status;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    Optional<Room> findByRoomNumber(int number);
}
