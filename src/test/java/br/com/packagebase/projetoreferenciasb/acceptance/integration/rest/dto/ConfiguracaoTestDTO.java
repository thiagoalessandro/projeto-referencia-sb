package br.com.packagebase.projetoreferenciasb.acceptance.integration.rest.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
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

    public static ConfiguracaoTestDTO mock(Long id, String nome, String valor) {
        ConfiguracaoTestDTO configuracaoTestDTO = new ConfiguracaoTestDTO();
        configuracaoTestDTO.setId(id);
        configuracaoTestDTO.setNome(nome);
        configuracaoTestDTO.setValor(valor);
        configuracaoTestDTO.setUsuarioAtualizacao(RandomStringUtils.randomAlphabetic(10));
        return configuracaoTestDTO;
    }
}

