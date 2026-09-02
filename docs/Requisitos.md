# 📄 Requisitos do Sistema - 360 Academics

## ✅ Requisitos Funcionais
- Cadastro de eventos
- Gerenciamento de inscrições
- Controle de presença
- Emissão de certificados digitais
- Cadastro de palestrantes e atividades
- Histórico de participação

## ⚙️ Requisitos Não Funcionais
- Segurança e autenticação (login/logout por sessão, para `Participante`, `Organizador` e `Palestrante`)
- Interface intuitiva e responsiva
- Escalabilidade para múltiplos eventos
- Disponibilidade e confiabilidade
- Aplicação baseada em ambiente **Web**

## 🧩 Entidades do Domínio
- **Usuario** (base abstrata: `Participante`, `Organizador`, `Palestrante`)
- **Evento**
- **Atividade**
- **Inscricao**
- **Presenca**
- **Certificado**
- **HistoricoParticipacao**

Ver [DIAGRAMA_CLASSE_360.md](DIAGRAMA_CLASSE_360.md) para o diagrama de classes atualizado.

## 🏗️ Arquitetura
Todo o fluxo de cada entidade segue a separação `Controller` → `Service` (regras de negócio) → `Repository` (persistência mock, em memória). Erros de regra de negócio são sinalizados por uma exceção comum (`RegraNegocioException`) e exibidos na tela de origem.

## 📌 Regras de Negócio
- Cada evento possui um limite máximo de vagas (`limiteVagas`), definido pelo organizador; novas inscrições são recusadas quando o limite é atingido.
- Certificados só podem ser emitidos para participantes com presença confirmada na atividade correspondente.
- Um participante não pode ter presença confirmada em duas atividades que ocorram no mesmo horário.
- Palestrantes só podem emitir certificados vinculados às atividades que eles próprios ministraram (validado pelo palestrante autenticado).
- O histórico de participação é registrado automaticamente para o participante quando um certificado de conclusão é emitido.
