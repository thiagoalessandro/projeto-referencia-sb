package br.com.packagebase.projetoreferenciasb.controller.ws.rest;

import br.com.packagebase.projetoreferenciasb.annotation.LogApp;
import br.com.packagebase.projetoreferenciasb.controller.resource.ResourceWSRest;
import br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.response.LogTransacionalResponseDTO;
import br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.response.Response;
import br.com.packagebase.projetoreferenciasb.domain.DominioOperacao;
import br.com.packagebase.projetoreferenciasb.domain.DominioRecurso;
import br.com.packagebase.projetoreferenciasb.exception.ValidationException;
import br.com.packagebase.projetoreferenciasb.model.LogTransacional;
import br.com.packagebase.projetoreferenciasb.service.LogTransacionalService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Log Transacional", tags = "Log Transacional")
@RestController
@RequestMapping(ResourceWSRest.LOG_TRANSACIONAL)
public class LogTransacionalRestController extends AbstractRestController {

    private final LogTransacionalService logTransacionalService;

    public LogTransacionalRestController(LogTransacionalService logTransacionalService) {
        this.logTransacionalService = logTransacionalService;
    }

    @GetMapping
    @LogApp(recurso = DominioRecurso.LOG_TRANSACIONAL, operacao = DominioOperacao.CONSULTAR)
    @ApiOperation(value = "Consultar log transacional")
    public ResponseEntity<Response<Page<LogTransacionalResponseDTO>>> search(@RequestParam(value = "search") String search,
                                                                  @RequestParam(name = "page") Integer page,
                                                                  @RequestParam(name = "size") Integer size) throws ValidationException {
        Response<Page<LogTransacionalResponseDTO>> response = new Response<>();
        Page<LogTransacionalResponseDTO> logTransacionalPage;
        logTransacionalPage = logTransacionalService.search(search, page, size);
        response.setData(logTransacionalPage);
        return ResponseEntity.ok(response);
    }

}
