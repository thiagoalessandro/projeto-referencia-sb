package br.com.packagebase.projetoreferenciasb.acceptance.features.configuracao;

import br.com.packagebase.projetoreferenciasb.controller.resource.ResourceWSRest;
import br.com.packagebase.projetoreferenciasb.acceptance.integration.rest.AbstractRestTest;
import br.com.packagebase.projetoreferenciasb.acceptance.integration.rest.dto.ConfiguracaoTestDTO;
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


public class ConfiguracaoValidaTests extends AbstractRestTest<Configuracao, ConfiguracaoService> {

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

    @Dada("^uma configuracao valida$")
    public void configuracaoValida() {
        requestDTO = ConfiguracaoTestDTO.mock(null, "SISTEMA", "SB");
    }

    @Quando("^a configuracao valida e cadastrada$")
    public void configuracaoValidaCadastrada()  throws Throwable {
        result = getMockMvc().perform(
                post(ResourceWSRest.CONFIGURACAO)
                        .content(requestDTO.toJson())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print());
    }

    @Entao("^a configuracao valida e salva$")
    public void configuracaoValidaSalva()  throws Throwable {
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors").isArray())
                .andReturn();
    }

}
