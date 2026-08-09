package br.edu.iff.ccc._academics.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class AtividadeRequest {

    private String titulo;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime horarioInicio;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime horarioFim;

    private String local;
    private Long palestranteId;

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

    public Long getPalestranteId() {
        return palestranteId;
    }

    public void setPalestranteId(Long palestranteId) {
        this.palestranteId = palestranteId;
    }

}
