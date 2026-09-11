package br.edu.iff.ccc._academics.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Usuario;

/**
 * Consulta a tabela de usuários como um todo, independentemente do tipo
 * (participante, organizador ou palestrante), porque o e-mail é único
 * entre todos eles.
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<Usuario, UUID> {

    boolean existsByEmail(String email);

    boolean existsByEmailAndIdNot(String email, UUID id);

}
