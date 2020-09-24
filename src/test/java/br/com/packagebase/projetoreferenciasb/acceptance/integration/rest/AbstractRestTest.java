package br.com.packagebase.projetoreferenciasb.acceptance.integration.rest;

import br.com.packagebase.projetoreferenciasb.ContextTests;
import br.com.packagebase.projetoreferenciasb.model.AbstractEntity;
import br.com.packagebase.projetoreferenciasb.service.GenericService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

public abstract class AbstractRestTest<T extends AbstractEntity, S extends GenericService> extends ContextTests {

    @Getter
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    protected void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    protected abstract void init();
    protected abstract void clean() throws IOException;

    protected Long getIdFromResponse(MockHttpServletResponse response) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(response.getContentAsString());
        Long id;
        if (jsonNode.get("data") != null)
            id = jsonNode.get("data").get("id").asLong();
        else
            id = jsonNode.get(0).get("data").get("id").asLong();
        return id;
    }

    protected T getEntity(MvcResult result) throws IOException {
        Long id = getIdFromResponse(result.getResponse());
        return (T) getService().findById(id).get();
    }

    protected void deleteDataFromMvcResult(ResultActions result) {
        Long id;
        if(result != null) {
            try {
                id = getIdFromResponse(result.andReturn().getResponse());
                delete(id);
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected void delete(Long id) {
        if(id != null) {
            try {
                getService().deleteById(id);
            } catch (NullPointerException ignored) {}
        }
    }

    protected abstract S getService();

}
