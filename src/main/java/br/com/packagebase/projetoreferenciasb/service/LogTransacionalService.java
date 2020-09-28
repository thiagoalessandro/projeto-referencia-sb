package br.com.packagebase.projetoreferenciasb.service;

import br.com.packagebase.projetoreferenciasb.component.ModelMapperApp;
import br.com.packagebase.projetoreferenciasb.controller.ws.rest.dto.response.LogTransacionalResponseDTO;
import br.com.packagebase.projetoreferenciasb.exception.ValidationException;
import br.com.packagebase.projetoreferenciasb.model.LogTransacional;
import br.com.packagebase.projetoreferenciasb.repository.LogTransacionalRepository;
import br.com.packagebase.projetoreferenciasb.repository.specification.CustomRsqlVisitor;
import cz.jirutka.rsql.parser.RSQLParser;
import cz.jirutka.rsql.parser.ast.Node;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
public class LogTransacionalService {

    private final LogTransacionalRepository repository;
    private final ModelMapperApp modelMapperApp;

    LogTransacionalService(LogTransacionalRepository repository,
                           ModelMapperApp modelMapperApp) {
        this.repository = repository;
        this.modelMapperApp = modelMapperApp;
    }

    @Async
    @EventListener(LogTransacional.class)
    @Transactional(propagation = Propagation.REQUIRED)
    public void onCreate(LogTransacional logTransacional) {
        log.info("Registrando log transacional...");
        log.debug("LogTransacional {}...", logTransacional.toString());
        repository.save(logTransacional);
    }

    public Page<LogTransacionalResponseDTO> search(String search, Integer page, Integer size) throws ValidationException {
        try {
            Node rootNode = new RSQLParser().parse(search);
            Specification<LogTransacional> spec = rootNode.accept(new CustomRsqlVisitor<>());
            Pageable pageable = PageRequest.of(page, size, Sort.by("dataHoraInicio").descending());
            Page<LogTransacional> logTransacionalPage = repository.findAll(spec, pageable);
            return logTransacionalPage.map(logTransacional -> modelMapperApp.map(logTransacional, LogTransacionalResponseDTO.class));
        } catch (Exception e) {
            throw new ValidationException(e.getMessage());
        }
    }

}
