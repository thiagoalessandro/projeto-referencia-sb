package br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.request;

import br.com.packagebase.projetoreferenciasb.domain.DominioSituacaoRegistro;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class AbstractRequestDTO implements Serializable {

    @ApiModelProperty
    @JsonProperty(value = "situacaoRegistro")
    private DominioSituacaoRegistro situacaoRegistro;

}
