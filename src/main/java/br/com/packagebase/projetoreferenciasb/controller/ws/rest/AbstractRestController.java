package br.com.packagebase.projetoreferenciasb.controller.ws.rest;

import br.com.packagebase.projetoreferenciasb.component.MessagesApp;
import br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.response.Response;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@ApiResponses({
        @ApiResponse(code = 200, message = "Requisição concluída com sucesso"),
        @ApiResponse(code = 400, message = "Erro de validação"),
        @ApiResponse(code = 500, message = "Erro ao processar requisição no servidor", response = Response.class)
})
public abstract class AbstractRestController<T> {

    @Autowired
    private MessagesApp messagesApp;

    public ResponseEntity<Response> responseDeleteOk() {
        Response<String> response = new Response<>();
        response.setData(messagesApp.get("message.repository.default.data.delete.success"));
        return ResponseEntity.ok(response);
    }

}
