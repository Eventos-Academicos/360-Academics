package br.edu.iff.ccc._academics.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class EventoRequest {

    @NotBlank(message = "O nome do evento é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve conter entre 3 e 100 caracteres.")
    private String nome;

    @Size(max = 255, message = "A descrição deve conter no máximo 255 caracteres.")
    private String descricao;

    @Size(max = 100, message = "O local deve conter no máximo 100 caracteres.")
    private String local;

    @NotNull(message = "A data de início é obrigatória.")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataInicio;

    @NotNull(message = "A data de fim é obrigatória.")
    @DateTimeFormat(iso = ISO.DATE)
    private LocalDate dataFim;

    @Min(value = 1, message = "O limite de vagas deve ser no mínimo 1.")
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
