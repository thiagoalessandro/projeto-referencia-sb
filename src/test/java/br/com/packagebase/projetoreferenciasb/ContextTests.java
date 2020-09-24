package br.com.packagebase.projetoreferenciasb;

import lombok.extern.log4j.Log4j2;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@Log4j2
@SpringBootTest
@ContextConfiguration
public class ContextTests {

    @Test
    public void context(){
        log.info("Contexto iniciado");
    }
}
