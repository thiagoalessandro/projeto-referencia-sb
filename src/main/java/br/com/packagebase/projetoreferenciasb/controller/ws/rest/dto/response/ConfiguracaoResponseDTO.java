package br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class ConfiguracaoResponseDTO extends AbstractResponseDTO {

    @ApiModelProperty
    private Long id;

    @ApiModelProperty(required = true)
    @JsonProperty(value = "nome")
    private String nome;

    @ApiModelProperty(required = true)
    @JsonProperty(value = "valor")
    private String valor;

}
