package br.edu.iff.ccc._academics.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Organizador;

@Repository
public interface OrganizadorRepositorio extends JpaRepository<Organizador, UUID> {
}
