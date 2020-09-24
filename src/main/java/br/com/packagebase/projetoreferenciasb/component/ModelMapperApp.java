package br.com.packagebase.projetoreferenciasb.component;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ConfigurationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Component
public class ModelMapperApp {

    private ModelMapper modelMapperStrict;
    private ModelMapper modelMapperDefault;

    private List<String> propertyMapList;

    @PostConstruct
    private void init() {
        propertyMapList = new ArrayList<>();
        loadModelMapperStrict();
        loadModelMapperDefault();
    }

    public <D, T> D map(final T origem, Class<D> destino, PropertyMap... propertyMaps) {
        return modelMapperHandler(propertyMaps, origem, destino);
    }

    public <D, T> List<D> mapAll(final Collection<T> entityList, Class<D> outCLass, PropertyMap... propertyMaps) {
        return entityList.stream()
                .map(entity -> map(entity, outCLass, propertyMaps))
                .collect(Collectors.toList());
    }

    public <D, T> D modelMapperHandler(PropertyMap[] propertyMaps, Object origem, Class<D> destino) {
        String chavePropertyMap;
        if (propertyMaps != null && propertyMaps.length > 0) {
            chavePropertyMap = gerarChavePropertyMap(origem.getClass(), destino);
            if (!propertyMapList.contains(chavePropertyMap)) {
                Arrays.asList(propertyMaps).forEach(propertyMap -> {
                    try {
                        modelMapperStrict.addMappings(propertyMap);
                    } catch (ConfigurationException e) {
                        if (!e.getMessage().contains("mapping already exists")) {
                            log.error(e);
                        }
                    }
                });
                propertyMapList.add(chavePropertyMap);
            }
            return modelMapperStrict.map(origem, destino);
        }
        return modelMapperDefault.map(origem, destino);
    }

    private String gerarChavePropertyMap(Class origem, Class destino) {
        StringJoiner propertyMap = new StringJoiner("::");
        propertyMap.add(origem.getName());
        propertyMap.add(destino.getName());
        return propertyMap.toString();
    }

    public void loadModelMapperStrict() {
        modelMapperStrict = new ModelMapper();
        modelMapperStrict.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    public void loadModelMapperDefault() {
        modelMapperDefault = new ModelMapper();
    }

}
