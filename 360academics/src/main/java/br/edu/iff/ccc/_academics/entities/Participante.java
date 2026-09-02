package br.edu.iff.ccc._academics.entities;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("PARTICIPANTE")
public class Participante extends Usuario {

    @Column(name = "nr_matricula", length = 20)
    private String matricula;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

}
