package br.edu.iff.ccc._academics.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import br.edu.iff.ccc._academics.entities.Evento;

@DataJpaTest
class EventoRepositorioTest {

    @Autowired
    private EventoRepositorio repositorio;

    @Test
    void deveSalvarEventoEGerarId() {
        // Arrange
        Evento evento = novoEvento("Semana Academica de Computacao");

        // Act
        Evento salvo = repositorio.save(evento);

        // Assert
        assertNotNull(salvo.getId(), "O ID no formato UUID deveria ter sido gerado pelo banco");
        assertEquals("Semana Academica de Computacao", salvo.getNome());
    }

    @Test
    void deveBuscarEventoPorId() {
        // Arrange
        Evento salvo = repositorio.save(novoEvento("Workshop de Java"));

        // Act
        Optional<Evento> encontrado = repositorio.findById(salvo.getId());

        // Assert
        assertTrue(encontrado.isPresent());
        assertEquals("Workshop de Java", encontrado.get().getNome());
    }

    @Test
    void deveLancarExcecaoQuandoNomeDuplicado() {
        // Arrange
        repositorio.save(novoEvento("Congresso de Engenharia"));
        Evento duplicado = novoEvento("Congresso de Engenharia");

        // Act & Assert (restricao unique = true na coluna nm_evento)
        assertThrows(DataIntegrityViolationException.class, () -> {
            repositorio.save(duplicado);
            repositorio.flush();
        });
    }

    @Test
    void deveLancarExcecaoQuandoDataInicioNula() {
        // Arrange
        Evento semData = novoEvento("Evento sem data");
        semData.setDataInicio(null);

        // Act & Assert (restricao nullable = false na coluna dt_inicio)
        assertThrows(DataIntegrityViolationException.class, () -> {
            repositorio.save(semData);
            repositorio.flush();
        });
    }

    private Evento novoEvento(String nome) {
        Evento evento = new Evento();
        evento.setNome(nome);
        evento.setDescricao("Evento academico de teste");
        evento.setLocal("Auditorio Principal");
        evento.setDataInicio(LocalDate.of(2026, 9, 1));
        evento.setDataFim(LocalDate.of(2026, 9, 5));
        evento.setLimiteVagas(100);
        return evento;
    }

}
