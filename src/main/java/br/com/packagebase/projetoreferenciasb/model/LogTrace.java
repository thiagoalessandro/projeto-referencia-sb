package br.com.packagebase.projetoreferenciasb.model;

import br.com.packagebase.projetoreferenciasb.domain.DominioOperacao;
import br.com.packagebase.projetoreferenciasb.domain.DominioRecurso;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Entity
@ToString
@Table(name = "tbl_log_trace")
public class LogTrace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "dominio", length = 15, nullable = false)
    private DominioRecurso dominio;

    @Enumerated(EnumType.STRING)
    @Column(name = "operacao", length = 15, nullable = false)
    private DominioOperacao operacao;

    @Column(name = "chave", nullable = false)
    private Long chave;

    @Column(name = "trace_id", length = 15, nullable = false)
    private String traceId;

    @NotNull(message = "Usuário de atualização é obrigatório")
    @Column(name = "cd_usu_atu", length = 25, nullable = false)
    private String usuarioAtualizacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dh_atu", nullable = false)
    private Date dataHoraAtualizacao;

    public LogTrace(DominioRecurso dominioRecurso,
                    Long chave,
                    String traceId,
                    DominioOperacao dominioOperacao,
                    String usuarioAtualizacao) {
        this.dominio = dominioRecurso;
        this.chave = chave;
        this.traceId = traceId;
        this.operacao = dominioOperacao;
        this.dataHoraAtualizacao = new Date();
        this.usuarioAtualizacao = usuarioAtualizacao;
    }

}
