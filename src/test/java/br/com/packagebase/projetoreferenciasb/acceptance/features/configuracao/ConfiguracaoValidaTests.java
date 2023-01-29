package br.com.packagebase.projetoreferenciasb.acceptance.features.configuracao;

import br.com.packagebase.projetoreferenciasb.controller.resource.ResourceWSRest;
import br.com.packagebase.projetoreferenciasb.integration.rest.AbstractRestTest;
import br.com.packagebase.projetoreferenciasb.integration.rest.dto.ConfiguracaoTestDTO;
import br.com.packagebase.projetoreferenciasb.model.Configuracao;
import br.com.packagebase.projetoreferenciasb.service.ConfiguracaoService;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.pt.Dada;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;
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
        requestDTO = ConfiguracaoTestDTO.builder()
                .nome("SISTEMA")
                .valor("SB")
                .usuarioAtualizacao("ADMIN")
                .build();
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
