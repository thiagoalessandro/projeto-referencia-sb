package br.com.packagebase.projetoreferenciasb.acceptance.features.configuracao;

import br.com.packagebase.projetoreferenciasb.controller.resource.ResourceWSRest;
import br.com.packagebase.projetoreferenciasb.integration.rest.AbstractRestTest;
import br.com.packagebase.projetoreferenciasb.integration.rest.dto.ConfiguracaoTestDTO;
import br.com.packagebase.projetoreferenciasb.model.Configuracao;
import br.com.packagebase.projetoreferenciasb.service.ConfiguracaoService;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dada;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CadastrandoConfiguracaoValidaaTests extends AbstractRestTest<Configuracao, ConfiguracaoService> {

    @Autowired
    @Getter(AccessLevel.PROTECTED)
    private ConfiguracaoService service;

    private ConfiguracaoTestDTO requestDTO;

    private ResultActions result;

    @Before
    @Override
    public void init(){
        setup();
    }

    @After
    @Override
    public void clean() {
        deleteDataFromMvcResult(result);
    }

    @Dada("^uma configuracao validaa$")
    public void uma_configuracao_valida()  throws Throwable {
        requestDTO = ConfiguracaoTestDTO.mock(null, "SISTEMA", "SB");
    }

    @Quando("^a configuracao valida e cadastradaa$")
    public void a_configuracao_valida_e_cadastrada()  throws Throwable {
        result = getMockMvc().perform(
                post(ResourceWSRest.CONFIGURACAO)
                        .content(requestDTO.toJson())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print());
    }

    @Entao("^a configuracao valida e salvaa$")
    public void a_configuracao_valida_e_salva()  throws Throwable {
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isArray())
                .andReturn();
    }

}
