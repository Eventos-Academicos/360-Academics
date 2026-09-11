package br.edu.iff.ccc._academics.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro e atualização de uma atividade de evento")
public class AtividadeRequest {

    @Schema(description = "Título da atividade",
            example = "Palestra: Introdução ao Spring Boot",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O título da atividade é obrigatório.")
    @Size(min = 3, max = 100, message = "O título deve conter entre 3 e 100 caracteres.")
    private String titulo;

    @Schema(description = "Data e hora de início da atividade", example = "2026-10-05T09:00:00",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O horário de início é obrigatório.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime horarioInicio;

    @Schema(description = "Data e hora de término da atividade", example = "2026-10-05T11:00:00",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "O horário de fim é obrigatório.")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime horarioFim;

    @Schema(description = "Sala ou espaço onde a atividade acontece", example = "Sala 101 - Bloco A",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 100, message = "O local deve conter no máximo 100 caracteres.")
    private String local;

    @Schema(description = "UUID do palestrante responsável pela atividade",
            example = "3fa85f64-5717-4562-b3fc-2c963f66afa6",
            requiredMode = Schema.RequiredMode.REQUIRED)
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
