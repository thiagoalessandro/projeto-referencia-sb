package br.com.packagebase.projetoreferenciasb.component;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ConfigurationException;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

@Data
@Component
public class PropertiesApp {

    @Value("${app.build.version}")
    private String appBuildVersion;

    @Value("${app.credentials.user}")
    private String appCredentialsUser;

    @Value("${app.credentials.password}")
    private String appCredentialsPassword;

}
