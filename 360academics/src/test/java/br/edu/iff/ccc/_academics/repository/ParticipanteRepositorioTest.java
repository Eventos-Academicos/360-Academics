package br.edu.iff.ccc._academics.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.edu.iff.ccc._academics.entities.Participante;

@DataJpaTest
class ParticipanteRepositorioTest {

    @Autowired
    private ParticipanteRepositorio repositorio;

    @Test
    void deveSalvarParticipanteEGerarId() {
        // Arrange
        Participante participante = novoParticipante("Maria Silva", "maria@iff.edu.br", "2026001");

        // Act
        Participante salvo = repositorio.save(participante);

        // Assert
        assertNotNull(salvo.getId(), "O ID no formato UUID deveria ter sido gerado pelo banco");
        assertEquals("Maria Silva", salvo.getNome());
    }

    @Test
    void deveBuscarParticipantePorId() {
        // Arrange
        Participante salvo = repositorio.save(novoParticipante("Joao Souza", "joao@iff.edu.br", "2026002"));

        // Act
        Optional<Participante> encontrado = repositorio.findById(salvo.getId());

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals("joao@iff.edu.br", encontrado.get().getEmail());
    }

    @Test
    void deveLancarExcecaoQuandoEmailDuplicado() {
        // Arrange
        repositorio.save(novoParticipante("Ana Lima", "ana@iff.edu.br", "2026003"));
        Participante mesmoEmail = novoParticipante("Ana Paula", "ana@iff.edu.br", "2026004");

        // Act & Assert (restricao unique = true na coluna ds_email)
        assertThrows(DataIntegrityViolationException.class, () -> {
            repositorio.save(mesmoEmail);
            repositorio.flush();
        });
    }

    private Participante novoParticipante(String nome, String email, String matricula) {
        Participante participante = new Participante();
        participante.setNome(nome);
        participante.setEmail(email);
        participante.setSenha("123456");
        participante.setMatricula(matricula);
        return participante;
    }

}
