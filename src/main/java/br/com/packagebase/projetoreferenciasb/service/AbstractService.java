package br.com.packagebase.projetoreferenciasb.service;

import br.com.packagebase.projetoreferenciasb.component.MessagesApp;
import br.com.packagebase.projetoreferenciasb.component.ModelMapperApp;
import br.com.packagebase.projetoreferenciasb.domain.DominioSituacaoRegistro;
import br.com.packagebase.projetoreferenciasb.exception.ValidationDataNotFoundException;
import br.com.packagebase.projetoreferenciasb.exception.ValidationException;
import br.com.packagebase.projetoreferenciasb.model.AbstractEntity;
import br.com.packagebase.projetoreferenciasb.repository.GenericRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import javax.validation.ConstraintViolationException;
import java.util.List;

@Log4j2
public abstract class AbstractService<R extends GenericRepository, T extends AbstractEntity, ID> extends GenericService<R, T, ID>{

    @Autowired
    private ModelMapperApp modelMapperApp;

    @Autowired
    private MessagesApp messagesApp;

    AbstractService(R repository) {
        super(repository);
    }

    public T save(T entity, boolean edit) throws ValidationException {
        T entitySaved;
        if (edit && entity.getId() == null)
            throw new ValidationException(messagesApp.get("message.repository.default.data.notid"));
        try {
            if (entity.getSituacaoRegistro() == null)
                entity.setSituacaoRegistro(DominioSituacaoRegistro.ATIVO);
            log.debug("Entidade {}...", entity.toString());
            entitySaved = saveAndFlush(entity);
            return entitySaved;
        } catch (DataIntegrityViolationException e) {
            if (e.getCause().getCause().getMessage().contains("not-null"))
                throw new ValidationException(messagesApp.get("message.repository.default.data.notnull"));
            else if (e.getCause().getCause().getMessage().contains("duplicate"))
                throw new ValidationException(messagesApp.get("message.repository.default.data.duplicate"));
            else {
                log.error(e);
                throw new ValidationException(messagesApp.get("message.repository.default.data.error.unknown"));
            }
        }catch (ConstraintViolationException e){
            throw new ConstraintViolationException(e.getConstraintViolations());
        }catch (Exception e){
            log.error(e);
            throw new ValidationException(messagesApp.get("message.repository.default.data.error.unknown"));
        }
    }

    public void deleteLogical(ID id) throws ValidationException {
        T entity = findById(id).orElseThrow(ValidationDataNotFoundException::new);
        if(entity.getSituacaoRegistro() == DominioSituacaoRegistro.EXCLUIDO)
            throw new ValidationException(messagesApp.get("message.repository.default.data.delete.duplicate"));
        try{
            deleteLogical(entity);
        }catch (Exception e){
            log.error(e);
            throw new ValidationException(messagesApp.get("message.repository.default.data.error.unknown"));
        }
    }

    public List<?> convertToListDTO(List<? extends T> list, Class clazz){
        return modelMapperApp.mapAll(list, clazz, null);
    }

    public List<?> convertToListDTO(List<? extends T> list, Class clazz, PropertyMap... propertyMap) {
        return modelMapperApp.mapAll(list, clazz, propertyMap);
    }

    public Object convertToSingleDTO(T entity, Class clazz){
        return modelMapperApp.map(entity, clazz, null);
    }

    public Object convertToSingleDTO(T entity, Class clazz, PropertyMap... propertyMap) {
        return modelMapperApp.map(entity, clazz, propertyMap);
    }

    public Object mapSingleObjectGeneric(Object source, Class clazz){
        return modelMapperApp.map(source, clazz, null);
    }

    public Object mapSingleObjectGeneric(Object source, Class clazz, PropertyMap... propertyMap) {
        return modelMapperApp.map(source, clazz, propertyMap);
    }

    public List<?> mapAllObjectGeneric(List<?> list, Class clazz){
        return modelMapperApp.mapAll(list, clazz, null);
    }

    public List<?> mapAllObjectGeneric(List<?> list, Class clazz, PropertyMap... propertyMap) {
        return modelMapperApp.mapAll(list, clazz, propertyMap);
    }

}
