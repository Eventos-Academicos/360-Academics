package br.edu.iff.ccc._academics.controller.rest;

import static org.hamcrest.Matchers.endsWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.iff.ccc._academics.dto.EventoRequest;
import br.edu.iff.ccc._academics.entities.Evento;
import br.edu.iff.ccc._academics.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc._academics.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc._academics.service.EventoService;

@WebMvcTest(EventoRestController.class)
class EventoRestControllerTest {

    private static final String EVENTO_VALIDO = """
            {
              "nome": "Semana Academica de Computacao",
              "descricao": "Palestras e oficinas",
              "local": "Auditorio Central",
              "dataInicio": "2026-10-05",
              "dataFim": "2026-10-09",
              "limiteVagas": 150
            }
            """;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventoService service;

    @Test
    void deveRetornar400ComInvalidParamsQuandoPayloadInvalido() throws Exception {
        String payloadInvalido = """
                { "nome": "", "limiteVagas": 0 }
                """;

        mockMvc.perform(post("/api/v1/eventos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payloadInvalido))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.title").value("Dados Inválidos"))
                .andExpect(jsonPath("$.invalid_params.nome").exists())
                .andExpect(jsonPath("$.invalid_params.dataInicio").exists())
                .andExpect(jsonPath("$.invalid_params.dataFim").exists())
                .andExpect(jsonPath("$.invalid_params.limiteVagas").exists());

        verify(service, never()).criar(any(EventoRequest.class));
    }

    @Test
    void deveRetornar404PadronizadoQuandoEventoNaoExiste() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.buscarPorId(id)).thenThrow(new RecursoNaoEncontradoException("Evento não encontrado."));

        mockMvc.perform(get("/api/v1/eventos/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.title").value("Recurso Não Encontrado"))
                .andExpect(jsonPath("$.detail").value("Evento não encontrado."))
                .andExpect(jsonPath("$.instance").value("/api/v1/eventos/" + id));
    }

    @Test
    void deveRetornar201ComLocationQuandoEventoCriado() throws Exception {
        UUID id = UUID.randomUUID();
        Evento evento = new Evento();
        evento.setId(id);
        when(service.criar(any(EventoRequest.class))).thenReturn(evento);

        mockMvc.perform(post("/api/v1/eventos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(EVENTO_VALIDO))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", endsWith("/api/v1/eventos/" + id)));
    }

    @Test
    void deveRetornar409PadronizadoQuandoNomeDuplicado() throws Exception {
        when(service.criar(any(EventoRequest.class)))
                .thenThrow(new EntidadeDuplicadaException("Já existe um evento com esse nome."));

        mockMvc.perform(post("/api/v1/eventos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(EVENTO_VALIDO))
                .andExpect(status().isConflict())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_PROBLEM_JSON))
                .andExpect(jsonPath("$.title").value("Entidade Duplicada"));
    }

    @Test
    void deveRetornar204QuandoEventoRemovido() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/v1/eventos/{id}", id))
                .andExpect(status().isNoContent());

        verify(service).remover(id);
    }

}
