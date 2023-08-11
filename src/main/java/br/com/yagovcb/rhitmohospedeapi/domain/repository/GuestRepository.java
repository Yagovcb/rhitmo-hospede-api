package br.com.yagovcb.rhitmohospedeapi.domain.repository;

import br.com.yagovcb.rhitmohospedeapi.domain.model.Guest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {

}
