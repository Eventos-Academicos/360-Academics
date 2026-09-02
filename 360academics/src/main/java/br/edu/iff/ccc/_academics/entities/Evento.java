package br.edu.iff.ccc._academics.entities;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "nm_evento", nullable = false, unique = true, length = 100)
    private String nome;

    @Column(name = "ds_descricao", length = 255)
    private String descricao;

    @Column(name = "ds_local", length = 100)
    private String local;

    @Column(name = "dt_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "dt_fim", nullable = false)
    private LocalDate dataFim;

    @Column(name = "nr_limite_vagas", nullable = false)
    private int limiteVagas;

    public Evento() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
