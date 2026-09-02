package br.edu.iff.ccc._academics.service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import br.edu.iff.ccc._academics.dto.CertificadoRequest;
import br.edu.iff.ccc._academics.entities.Atividade;
import br.edu.iff.ccc._academics.entities.Certificado;
import br.edu.iff.ccc._academics.entities.Inscricao;
import br.edu.iff.ccc._academics.exception.RegraNegocioException;
import br.edu.iff.ccc._academics.repository.CertificadoRepositorio;

@Service
public class CertificadoService {

    private final CertificadoRepositorio repositorio;
    private final InscricaoService inscricaoService;
    private final AtividadeService atividadeService;
    private final PresencaService presencaService;
    private final HistoricoService historicoService;

    public CertificadoService(CertificadoRepositorio repositorio, InscricaoService inscricaoService,
            AtividadeService atividadeService, PresencaService presencaService, HistoricoService historicoService) {
        this.repositorio = repositorio;
        this.inscricaoService = inscricaoService;
        this.atividadeService = atividadeService;
        this.presencaService = presencaService;
        this.historicoService = historicoService;
    }

    public List<Certificado> listarTodos() {
        return repositorio.findAll();
    }

    public Certificado buscarPorId(UUID id) {
        return repositorio.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Certificado não encontrado."));
    }

    public Certificado gerar(UUID inscricaoId, UUID atividadeId, UUID palestranteEmissorId) {
        Atividade atividade = atividadeService.buscarPorId(atividadeId);
        Inscricao inscricao = inscricaoService.buscarPorId(inscricaoId);

        if (atividade.getPalestrante() == null
                || !atividade.getPalestrante().getId().equals(palestranteEmissorId)) {
            throw new RegraNegocioException(
                    "Somente o palestrante responsável pela atividade pode emitir este certificado.");
        }
        if (!presencaService.estaConfirmada(inscricaoId, atividadeId)) {
            throw new RegraNegocioException("Certificado só pode ser emitido para presença confirmada.");
        }

        Certificado certificado = new Certificado();
        certificado.setInscricao(inscricao);
        certificado.setAtividade(atividade);
        certificado.setDataEmissao(LocalDate.now());
        certificado.setCodigoValidacao(UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        repositorio.save(certificado);

        historicoService.registrarConclusao(inscricao.getParticipante(), atividade.getEvento());

        return certificado;
    }

    public Certificado atualizar(UUID id, CertificadoRequest request) {
        Certificado certificado = buscarPorId(id);
        certificado.setCodigoValidacao(request.getCodigoValidacao());
        return repositorio.save(certificado);
    }

    public void remover(UUID id) {
        repositorio.deleteById(id);
    }

}
