package br.edu.iff.ccc._academics.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ORGANIZADOR")
public class Organizador extends Usuario {
}
