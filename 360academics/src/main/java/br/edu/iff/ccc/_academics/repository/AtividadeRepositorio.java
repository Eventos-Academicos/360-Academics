package br.edu.iff.ccc._academics.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Atividade;

@Repository
public interface AtividadeRepositorio extends JpaRepository<Atividade, UUID> {

    List<Atividade> findByEventoId(UUID eventoId);

}
