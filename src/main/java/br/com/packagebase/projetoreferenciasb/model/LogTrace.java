package br.com.packagebase.projetoreferenciasb.model;

import br.com.packagebase.projetoreferenciasb.domain.DominioOperacao;
import br.com.packagebase.projetoreferenciasb.domain.DominioRecurso;
import lombok.Data;
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
    @Column(name = "dominio", length = 15)
    private DominioRecurso dominio;

    @Enumerated(EnumType.STRING)
    @Column(name = "operacao", length = 15)
    private DominioOperacao operacao;

    @Column(name = "chave")
    private Long chave;

    @Column(name = "trace_id", length = 15, nullable = false)
    private String traceId;

    @Column(name = "req_path")
    private String requestPath;

    @Column(name = "resp_status")
    private Integer responseStatus;

    @Column(name = "resp_errors")
    private String responseErrors;

    @Column(name = "exception")
    private String exception;

    @Column(name = "req_method", length = 10)
    private String requestMethod;

    @Column(name = "req_query")
    private String requestQuery;

    @Column(name = "ip", length = 20)
    private String ip;

    @NotNull(message = "Usuário é obrigatório")
    @Column(name = "cd_usu", length = 25, nullable = false)
    private String usuario;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dh_ini", nullable = false)
    private Date dataHoraInicio;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dh_fim", nullable = false)
    private Date dataHoraFim;

    @Column(name = "resp_time_ms", nullable = false)
    private Integer tempoRespostaMs;

    public Integer calcularTempoResposaMilisegundos() {
        if (dataHoraInicio != null && dataHoraFim != null) {
            Long tempoResposta = dataHoraFim.getTime() - dataHoraInicio.getTime();
            return tempoResposta.intValue();
        }
        return null;
    }

}
