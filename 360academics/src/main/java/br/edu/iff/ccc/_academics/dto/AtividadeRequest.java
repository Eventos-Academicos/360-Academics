package br.edu.iff.ccc._academics.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class AtividadeRequest {

    @NotBlank(message = "O título da atividade é obrigatório.")
    @Size(min = 3, max = 100, message = "O título deve conter entre 3 e 100 caracteres.")
    private String titulo;

    @NotNull(message = "O horário de início é obrigatório.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime horarioInicio;

    @NotNull(message = "O horário de fim é obrigatório.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime horarioFim;

    @Size(max = 100, message = "O local deve conter no máximo 100 caracteres.")
    private String local;

    @NotNull(message = "Selecione o palestrante responsável pela atividade.")
    private UUID palestranteId;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDateTime getHorarioInicio() {
        return horarioInicio;
    }

    public void setHorarioInicio(LocalDateTime horarioInicio) {
        this.horarioInicio = horarioInicio;
    }

    public LocalDateTime getHorarioFim() {
        return horarioFim;
    }

    public void setHorarioFim(LocalDateTime horarioFim) {
        this.horarioFim = horarioFim;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public UUID getPalestranteId() {
        return palestranteId;
    }

    public void setPalestranteId(UUID palestranteId) {
        this.palestranteId = palestranteId;
    }

}
