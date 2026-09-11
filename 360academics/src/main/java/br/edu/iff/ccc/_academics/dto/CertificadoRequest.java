package br.edu.iff.ccc._academics.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CertificadoRequest {

    @NotBlank(message = "O código de validação é obrigatório.")
    @Size(min = 4, max = 20, message = "O código de validação deve conter entre 4 e 20 caracteres.")
    private String codigoValidacao;

    public String getCodigoValidacao() {
        return codigoValidacao;
    }

    public void setCodigoValidacao(String codigoValidacao) {
        this.codigoValidacao = codigoValidacao;
    }

}
