package br.edu.iff.ccc._academics.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Dados para cadastro e atualização de um palestrante")
public class PalestranteRequest {

    @Schema(description = "Nome completo do palestrante", example = "Helena Duarte",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O nome é obrigatório.")
    @Size(min = 3, max = 100, message = "O nome deve conter entre 3 e 100 caracteres.")
    private String nome;

    @Schema(description = "E-mail do palestrante, único entre todos os usuários",
            example = "helena.duarte@iff.edu.br",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "Informe um e-mail válido.")
    @Size(max = 100, message = "O e-mail deve conter no máximo 100 caracteres.")
    private String email;

    // Em branco na edição significa "manter a senha atual".
    @Schema(description = "Senha de acesso. Em branco na atualização mantém a senha atual",
            example = "senha123", format = "password",
            requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @Size(max = 100, message = "A senha deve conter no máximo 100 caracteres.")
    private String senha;

    @Schema(description = "Área de atuação do palestrante", example = "Engenharia de Software",
            requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "A especialidade é obrigatória.")
    @Size(max = 100, message = "A especialidade deve conter no máximo 100 caracteres.")
    private String especialidade;

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

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

}
