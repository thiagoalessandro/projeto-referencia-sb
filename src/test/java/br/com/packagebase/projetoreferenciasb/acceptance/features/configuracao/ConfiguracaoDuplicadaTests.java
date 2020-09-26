package br.com.packagebase.projetoreferenciasb.acceptance.features.configuracao;

import br.com.packagebase.projetoreferenciasb.integration.rest.AbstractRestTest;
import br.com.packagebase.projetoreferenciasb.integration.rest.dto.ConfiguracaoTestDTO;
import br.com.packagebase.projetoreferenciasb.controller.resource.ResourceWSRest;
import br.com.packagebase.projetoreferenciasb.domain.DominioSituacaoRegistro;
import br.com.packagebase.projetoreferenciasb.model.Configuracao;
import br.com.packagebase.projetoreferenciasb.service.ConfiguracaoService;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.pt.Dada;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Log4j2
public class ConfiguracaoDuplicadaTests extends AbstractRestTest<Configuracao, ConfiguracaoService> {

    @Autowired
    @Getter(AccessLevel.PROTECTED)
    private ConfiguracaoService service;

    private Configuracao configuracaoInicial = new Configuracao();

    private ConfiguracaoTestDTO requestDTO;

    private ResultActions result;

    @Before
    @Override
    public void init() {
        setup();
    }

    @After
    @Override
    public void clean() {
        delete(configuracaoInicial.getId());
    }

    @Dada("^uma configuracao duplicada$")
    public void configuracaoDuplicada() {
        preparaConfiguracaoDuplicada();
        requestDTO = ConfiguracaoTestDTO.mock(null, "SISTEMA", "SB");
    }

    @Quando("^a configuracao duplicada e cadastrada$")
    public void configuracaoDuplicadaCadastrada() throws Throwable {
        result = getMockMvc().perform(
                post(ResourceWSRest.CONFIGURACAO)
                        .content(requestDTO.toJson())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print());
    }

    @Entao("^a configuracao duplicada e rejeitada$")
    public void configuracaoDuplicadaRejeitada() throws Throwable {
        result.andExpect(status().isBadRequest()).andReturn();
    }

    private void preparaConfiguracaoDuplicada() {
        configuracaoInicial.setNome("SISTEMA");
        configuracaoInicial.setValor("TESTE");
        configuracaoInicial.setSituacaoRegistro(DominioSituacaoRegistro.ATIVO);
        configuracaoInicial.setUsuarioAtualizacao("ADMIN");
        configuracaoInicial = service.saveAndFlush(configuracaoInicial);
    }

}
