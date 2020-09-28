package br.com.packagebase.projetoreferenciasb.integration.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracaoTestDTO extends AbstractRestTestDTO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty(value = "id")
    private Long id;

    @JsonProperty(value = "nome")
    private String nome;

    @JsonProperty(value = "valor")
    private String valor;

    @JsonProperty(value = "usuarioAtualizacao")
    private String usuarioAtualizacao;

}

