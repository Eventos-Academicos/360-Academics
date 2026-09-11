package br.edu.iff.ccc._academics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro e atualização de um participante")
public class ParticipanteRequest {

    @Schema(description = "Nome completo do participante", example = "Maria Silva",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve conter entre 3 e 100 caracteres.")
    private String nome;

    @Schema(description = "E-mail do participante, único entre todos os usuários",
            example = "maria.silva@iff.edu.br",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 100, message = "O e-mail deve conter no máximo 100 caracteres.")
    private String email;

    // A senha não é exigida aqui porque o cadastro administrativo de participantes
    // não a informa; no formulário de registro ela é obrigatória pela própria tela.
    @Schema(description = "Senha de acesso à plataforma (opcional no cadastro administrativo)",
            example = "senha123", format = "password",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 100, message = "A senha deve conter no máximo 100 caracteres.")
    private String senha;

    @Schema(description = "Matrícula institucional do participante", example = "20261001",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "A matrícula é obrigatória.")
    @Size(max = 20, message = "A matrícula deve conter no máximo 20 caracteres.")
    private String matricula;

    public ParticipanteRequest() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

}
