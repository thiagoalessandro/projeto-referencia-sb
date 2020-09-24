package br.com.packagebase.projetoreferenciasb.acceptance.integration.rest.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public abstract class AbstractRestTestDTO implements Serializable {

    public String toJson() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

}
