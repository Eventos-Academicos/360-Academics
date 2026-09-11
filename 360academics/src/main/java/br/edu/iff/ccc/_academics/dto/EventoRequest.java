package br.edu.iff.ccc._academics.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro e atualização de um evento acadêmico")
public class EventoRequest {

    @Schema(description = "Nome do evento, único na plataforma",
            example = "Semana Acadêmica de Computação 2026",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome do evento é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve conter entre 3 e 100 caracteres.")
    private String nome;

    @Schema(description = "Descrição do evento",
            example = "Palestras, minicursos e oficinas sobre tecnologia e pesquisa",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 255, message = "A descrição deve conter no máximo 255 caracteres.")
    private String descricao;

    @Schema(description = "Local de realização do evento",
            example = "Auditório Central do IFF",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 100, message = "O local deve conter no máximo 100 caracteres.")
    private String local;

    @Schema(description = "Data de início do evento", example = "2026-10-05",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A data de início é obrigatória.")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataInicio;

    @Schema(description = "Data de término do evento", example = "2026-10-09",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "A data de fim é obrigatória.")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataFim;

    @Schema(description = "Quantidade máxima de inscrições aceitas", example = "150",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @Positive(message = "O limite de vagas deve ser no mínimo 1.")
    private int limiteVagas;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public int getLimiteVagas() {
        return limiteVagas;
    }

    public void setLimiteVagas(int limiteVagas) {
        this.limiteVagas = limiteVagas;
    }

}
