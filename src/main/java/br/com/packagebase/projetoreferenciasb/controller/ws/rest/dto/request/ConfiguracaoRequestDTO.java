package br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel
public class ConfiguracaoRequestDTO extends AbstractRequestDTO {

    @ApiModelProperty
    private Long id;

    @ApiModelProperty(required = true)
    @JsonProperty(value = "nome")
    private String nome;

    @ApiModelProperty(required = true)
    @JsonProperty(value = "valor")
    private String valor;

    @ApiModelProperty(required = true)
    @JsonProperty(value = "usuarioAtualizacao")
    private String usuarioAtualizacao;

}

