package br.edu.iff.ccc._academics.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.HistoricoParticipacao;

@Repository
public interface HistoricoRepositorio extends JpaRepository<HistoricoParticipacao, UUID> {

    Optional<HistoricoParticipacao> findByParticipanteIdAndEventoId(UUID participanteId, UUID eventoId);

}
