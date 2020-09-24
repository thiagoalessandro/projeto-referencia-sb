package br.com.packagebase.projetoreferenciasb.service;

import br.com.packagebase.projetoreferenciasb.domain.DominioSituacaoRegistro;
import br.com.packagebase.projetoreferenciasb.model.AbstractEntity;
import br.com.packagebase.projetoreferenciasb.repository.GenericRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Log4j2
public abstract class GenericService<R extends GenericRepository, T extends AbstractEntity, ID> {

    R repository;

    GenericService(R repository) {
        this.repository = repository;
    }

    public T save(T entity) {
        return (T) repository.save(entity);
    }

    public T saveAndFlush(T entity) {
        return (T) repository.saveAndFlush(entity);
    }

    public Iterable<? extends T> saveAll(Iterable<? extends T> entities) {
        return repository.saveAll(entities);
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public void deleteLogical(T entity) {
        entity.setSituacaoRegistro(DominioSituacaoRegistro.EXCLUIDO);
        repository.save(entity);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<T> findByAtivo() {
        return repository.findBySituacaoRegistro(DominioSituacaoRegistro.ATIVO);
    }

    public Pageable generatePageable(Integer page, Integer size) {
        if (page == null || size == null)
            return null;
        return PageRequest.of(page, size);
    }

}
