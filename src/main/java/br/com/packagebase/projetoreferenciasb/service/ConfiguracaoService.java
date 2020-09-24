package br.com.packagebase.projetoreferenciasb.service;

import br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.request.ConfiguracaoRequestDTO;
import br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.response.ConfiguracaoResponseDTO;
import br.com.packagebase.projetoreferenciasb.exception.ValidationDataNotFoundException;
import br.com.packagebase.projetoreferenciasb.exception.ValidationException;
import br.com.packagebase.projetoreferenciasb.model.Configuracao;
import br.com.packagebase.projetoreferenciasb.repository.ConfiguracaoRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;

@Log4j2
@Service
public class ConfiguracaoService extends AbstractService<ConfiguracaoRepository, Configuracao, Long> {

    ConfiguracaoService(ConfiguracaoRepository repository) {
        super(repository);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ValidationException.class)
    public ConfiguracaoResponseDTO register(@Valid ConfiguracaoRequestDTO requestDTO) throws ValidationException {
        Configuracao configuracao = (Configuracao) mapSingleObjectGeneric(requestDTO, Configuracao.class);
        configuracao = save(configuracao, false);
        return (ConfiguracaoResponseDTO) convertToSingleDTO(configuracao, ConfiguracaoResponseDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ValidationException.class)
    public ConfiguracaoResponseDTO edit(ConfiguracaoRequestDTO requestDTO) throws ValidationException {
        Configuracao configuracao = (Configuracao) mapSingleObjectGeneric(requestDTO, Configuracao.class);
        configuracao = save(configuracao, true);
        return (ConfiguracaoResponseDTO) convertToSingleDTO(configuracao, ConfiguracaoResponseDTO.class);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = ValidationException.class)
    public void delete(Long id) throws ValidationException {
        this.deleteLogical(id);
    }

    public ConfiguracaoResponseDTO search(String nome) throws ValidationException {
        Configuracao configuracao = findByNome(nome).orElseThrow(ValidationDataNotFoundException::new);
        return (ConfiguracaoResponseDTO) convertToSingleDTO(configuracao, ConfiguracaoResponseDTO.class);
    }

    public Optional<Configuracao> findByNome(String nome) {
        return repository.findByNome(nome);
    }

}
