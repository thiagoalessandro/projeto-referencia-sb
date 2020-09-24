package br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.response;

import br.com.packagebase.projetoreferenciasb.domain.DominioSituacaoRegistro;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class AbstractResponseDTO implements Serializable {

    @JsonProperty(value = "usuarioAtualizacao")
    private String usuarioAtualizacao;

    @JsonProperty(value = "situacaoRegistro")
    private DominioSituacaoRegistro situacaoRegistro;

    @JsonProperty(value = "dataHoraAtualizacao")
    private Long dataHoraAtualizacao;
}
