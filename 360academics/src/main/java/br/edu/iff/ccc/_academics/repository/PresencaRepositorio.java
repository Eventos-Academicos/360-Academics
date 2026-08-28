package br.edu.iff.ccc._academics.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Presenca;

@Repository
public interface PresencaRepositorio extends JpaRepository<Presenca, UUID> {

    Optional<Presenca> findByInscricaoIdAndAtividadeId(UUID inscricaoId, UUID atividadeId);

    List<Presenca> findByConfirmadaTrueAndInscricao_Participante_Id(UUID participanteId);

}
