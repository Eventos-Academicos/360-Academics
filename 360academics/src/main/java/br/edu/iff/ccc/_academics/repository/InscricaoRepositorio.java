package br.edu.iff.ccc._academics.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Inscricao;

@Repository
public interface InscricaoRepositorio extends JpaRepository<Inscricao, UUID> {

    List<Inscricao> findByEventoId(UUID eventoId);

    List<Inscricao> findByParticipanteId(UUID participanteId);

}
