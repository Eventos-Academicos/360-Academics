package br.edu.iff.ccc._academics.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Palestrante;

@Repository
public interface PalestranteRepositorio extends JpaRepository<Palestrante, UUID> {
}
