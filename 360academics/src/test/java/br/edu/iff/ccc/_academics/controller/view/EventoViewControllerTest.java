package br.edu.iff.ccc._academics.controller.view;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import br.edu.iff.ccc._academics.dto.EventoRequest;
import br.edu.iff.ccc._academics.exception.EntidadeDuplicadaException;
import br.edu.iff.ccc._academics.exception.RecursoNaoEncontradoException;
import br.edu.iff.ccc._academics.service.EventoService;

@WebMvcTest(EventoViewController.class)
class EventoViewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EventoService service;

    @Test
    void deveRecarregarFormularioComErrosQuandoDadosInvalidos() throws Exception {
        mockMvc.perform(post("/eventos")
                .sessionAttr("tipoUsuario", "ORGANIZADOR")
                .param("nome", "")
                .param("descricao", "Evento de teste")
                .param("local", "Auditorio")
                .param("dataInicio", "")
                .param("dataFim", "")
                .param("limiteVagas", "0"))
                .andExpect(status().isOk())
                .andExpect(view().name("eventos/form"))
                .andExpect(model().attributeHasFieldErrors("eventoRequest",
                        "nome", "dataInicio", "dataFim", "limiteVagas"));

        verify(service, never()).criar(any(EventoRequest.class));
    }

    @Test
    void deveSalvarERedirecionarQuandoDadosValidos() throws Exception {
        mockMvc.perform(post("/eventos")
                .sessionAttr("tipoUsuario", "ORGANIZADOR")
                .param("nome", "Semana Academica")
                .param("descricao", "Evento de teste")
                .param("local", "Auditorio")
                .param("dataInicio", "2026-09-01")
                .param("dataFim", "2026-09-05")
                .param("limiteVagas", "50"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/eventos"));

        verify(service).criar(any(EventoRequest.class));
    }

    @Test
    void deveRenderizarPaginaDeNaoEncontradoQuandoEventoNaoExiste() throws Exception {
        UUID id = UUID.randomUUID();
        when(service.buscarPorId(id))
                .thenThrow(new RecursoNaoEncontradoException("Evento não encontrado."));

        mockMvc.perform(get("/eventos/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/404"))
                .andExpect(model().attribute("mensagem", "Evento não encontrado."));
    }

    @Test
    void deveRenderizarPaginaDeConflitoQuandoNomeDuplicado() throws Exception {
        when(service.criar(any(EventoRequest.class)))
                .thenThrow(new EntidadeDuplicadaException("Já existe um evento com esse nome."));

        mockMvc.perform(post("/eventos")
                .sessionAttr("tipoUsuario", "ORGANIZADOR")
                .param("nome", "Semana Academica")
                .param("descricao", "Evento de teste")
                .param("local", "Auditorio")
                .param("dataInicio", "2026-09-01")
                .param("dataFim", "2026-09-05")
                .param("limiteVagas", "50"))
                .andExpect(status().isConflict())
                .andExpect(view().name("error/409"));
    }

    @Test
    void deveRenderizarPaginaDeRegraDeNegocioQuandoNaoForOrganizador() throws Exception {
        mockMvc.perform(post("/eventos")
                .param("nome", "Semana Academica")
                .param("descricao", "Evento de teste")
                .param("local", "Auditorio")
                .param("dataInicio", "2026-09-01")
                .param("dataFim", "2026-09-05")
                .param("limiteVagas", "50"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("error/400"));

        verify(service, never()).criar(any(EventoRequest.class));
    }

}
