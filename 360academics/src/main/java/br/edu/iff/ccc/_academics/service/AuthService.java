package br.edu.iff.ccc._academics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.entities.Organizador;
import br.edu.iff.ccc._academics.entities.Palestrante;
import br.edu.iff.ccc._academics.entities.Participante;
import br.edu.iff.ccc._academics.entities.Usuario;
import br.edu.iff.ccc._academics.exception.RegraNegocioException;

@Service
public class AuthService {

    private final ParticipanteService participanteService;
    private final OrganizadorService organizadorService;
    private final PalestranteService palestranteService;

    public AuthService(ParticipanteService participanteService, OrganizadorService organizadorService,
            PalestranteService palestranteService) {
        this.participanteService = participanteService;
        this.organizadorService = organizadorService;
        this.palestranteService = palestranteService;
    }

    public Usuario autenticar(String email, String senha) {
        Usuario usuario = buscar(participanteService.listarTodos(), email, senha);
        if (usuario == null) {
            usuario = buscar(organizadorService.listarTodos(), email, senha);
        }
        if (usuario == null) {
            usuario = buscar(palestranteService.listarTodos(), email, senha);
        }
        if (usuario == null) {
            throw new RegraNegocioException("Email ou senha inválidos.");
        }
        return usuario;
    }

    public String tipoDe(Usuario usuario) {
        if (usuario instanceof Participante) {
            return "PARTICIPANTE";
        }
        if (usuario instanceof Organizador) {
            return "ORGANIZADOR";
        }
        if (usuario instanceof Palestrante) {
            return "PALESTRANTE";
        }
        return "USUARIO";
    }

    private Usuario buscar(List<? extends Usuario> usuarios, String email, String senha) {
        return usuarios.stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email))
                .filter(u -> u.getSenha() != null && u.getSenha().equals(senha))
                .findFirst()
                .orElse(null);
    }

}
