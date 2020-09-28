package br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.response;

import br.com.packagebase.projetoreferenciasb.domain.DominioOperacao;
import br.com.packagebase.projetoreferenciasb.domain.DominioRecurso;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@Data
@ApiModel
public class LogTransacionalResponseDTO {

    @ApiModelProperty
    private Long id;

    @JsonProperty(value = "dominio")
    private DominioRecurso dominio;

    @JsonProperty(value = "operacao")
    private DominioOperacao operacao;

    @JsonProperty(value = "chave")
    private Long chave;

    @JsonProperty(value = "rev")
    private Long revisao;

    @JsonProperty(value = "traceId")
    private String traceId;

    @JsonProperty(value = "requestPath")
    private String requestPath;

    @JsonProperty(value = "responseStatus")
    private Integer responseStatus;

    @JsonProperty(value = "responseErrors")
    private String responseErrors;

    @JsonProperty(value = "exception")
    private String exception;

    @JsonProperty(value = "requestMethod")
    private String requestMethod;

    @JsonProperty(value = "requestQuery")
    private String requestQuery;

    @JsonProperty(value = "ip")
    private String ip;

    @JsonProperty(value = "usuario")
    private String usuario;

    @JsonProperty(value = "dataHoraInicio")
    private Date dataHoraInicio;

    @JsonProperty(value = "dataHoraFim")
    private Date dataHoraFim;

    @JsonProperty(value = "tempoRespostaMs")
    private Integer tempoRespostaMs;

}
