package br.edu.iff.ccc._academics.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import br.edu.iff.ccc._academics.entities.Certificado;

@Repository
public class CertificadoRepositorio {

    private final List<Certificado> certificados = new ArrayList<>();
    private final AtomicLong proximoId = new AtomicLong(1);

    public Certificado salvar(Certificado certificado) {
        certificado.setId(proximoId.getAndIncrement());
        this.certificados.add(certificado);
        return certificado;
    }

    public List<Certificado> listarTodos() {
        return this.certificados;
    }

    public Certificado buscarPorId(Long id) {
        for (Certificado certificado : this.certificados) {
            if (certificado.getId() != null && certificado.getId().equals(id)) {
                return certificado;
            }
        }
        return null;
    }

    public void remover(Certificado certificado) {
        this.certificados.remove(certificado);
    }

}
