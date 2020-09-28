package br.com.packagebase.projetoreferenciasb.controller.ws.rest;

import br.com.packagebase.projetoreferenciasb.annotation.LogApp;
import br.com.packagebase.projetoreferenciasb.controller.resource.ResourceWSRest;
import br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.request.ConfiguracaoRequestDTO;
import br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.response.ConfiguracaoResponseDTO;
import br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.response.Response;
import br.com.packagebase.projetoreferenciasb.domain.DominioOperacao;
import br.com.packagebase.projetoreferenciasb.domain.DominioRecurso;
import br.com.packagebase.projetoreferenciasb.exception.ValidationException;
import br.com.packagebase.projetoreferenciasb.service.ConfiguracaoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Configurações", tags = "Configurações")
@RestController
@RequestMapping(ResourceWSRest.CONFIGURACAO)
public class ConfiguracaoRestController extends AbstractRestController {

    private final ConfiguracaoService configuracaoService;

    public ConfiguracaoRestController(ConfiguracaoService configuracaoService) {
        this.configuracaoService = configuracaoService;
    }

    @PostMapping
    @LogApp(recurso = DominioRecurso.CONFIGURACAO, operacao = DominioOperacao.CADASTRAR)
    @ApiOperation(value = "Cadastrar Configuração")
    public ResponseEntity<Response<ConfiguracaoResponseDTO>> cadastrar(@RequestBody ConfiguracaoRequestDTO configuracaoRequestDTO) throws ValidationException {
        Response<ConfiguracaoResponseDTO> response = new Response<>();
        ConfiguracaoResponseDTO responseData;
        responseData = configuracaoService.register(configuracaoRequestDTO);
        response.setData(responseData);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    @LogApp(recurso = DominioRecurso.CONFIGURACAO, operacao = DominioOperacao.EDITAR)
    @ApiOperation(value = "Editar Configuração")
    public ResponseEntity<Response<ConfiguracaoResponseDTO>> edit(@RequestBody ConfiguracaoRequestDTO configuracaoRequestDTO) throws ValidationException {
        Response<ConfiguracaoResponseDTO> response = new Response<>();
        ConfiguracaoResponseDTO responseData;
        responseData = configuracaoService.edit(configuracaoRequestDTO);
        response.setData(responseData);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @LogApp(recurso = DominioRecurso.CONFIGURACAO, operacao = DominioOperacao.EXCLUIR)
    @ApiOperation(value = "Excluir Configuração")
    public ResponseEntity<Response<String>> delete(@PathVariable("id") Long id) throws ValidationException {
        configuracaoService.delete(id);
        return responseDeleteOk();
    }

    @GetMapping("/{nome}")
    @LogApp(recurso = DominioRecurso.CONFIGURACAO, operacao = DominioOperacao.CONSULTAR)
    @ApiOperation(value = "Consultar Configuração")
    public ResponseEntity<Response<ConfiguracaoResponseDTO>> search(@PathVariable(name = "nome") String nome) throws ValidationException {
        Response<ConfiguracaoResponseDTO> response = new Response<>();
        ConfiguracaoResponseDTO configuracaoResponseDTO;
        configuracaoResponseDTO = configuracaoService.search(nome);
        response.setData(configuracaoResponseDTO);
        return ResponseEntity.ok(response);
    }

}
