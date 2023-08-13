package br.com.yagovcb.rhitmohospedeapi.domain.repository;

import br.com.yagovcb.rhitmohospedeapi.domain.enums.Status;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Guest;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Reservation;
import br.com.yagovcb.rhitmohospedeapi.domain.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    @Query(value = "select room.* from reservation r " +
            "join room room on room.id = r.id_room " +
            "where room.status = :status and r.checkin_date is null " +
            "and r.number_room_reserved is not null " +
            "and r.reservation_date between :dataInicial and :dataFinal", nativeQuery = true)
    List<Room> findAllRoomByStatus(Status status, LocalDate dataInicial, LocalDate dataFinal);

    List<Reservation> findAllByStatus(Status status);

    List<Reservation> findAllByReservationDateBetween(LocalDate dataInicial, LocalDate dataFinal);

    Optional<List<Reservation>> findAllByNumberRoomReserved(int number);

    Optional<Reservation> findByGuestAndReservationDate(Guest guest, LocalDate reservationDate);

    Optional<Reservation> findByReservationDateAndNumberRoomReserved(LocalDate reservationDate, int number);

    Optional<Reservation> findByCode(String code);

    boolean existsByCode(String code);
}
