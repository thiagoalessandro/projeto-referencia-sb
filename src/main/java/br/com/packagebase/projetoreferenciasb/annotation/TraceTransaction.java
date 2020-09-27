package br.com.packagebase.projetoreferenciasb.annotation;

import br.com.packagebase.projetoreferenciasb.domain.DominioOperacao;
import br.com.packagebase.projetoreferenciasb.domain.DominioRecurso;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface TraceTransaction {
    DominioRecurso recurso();
    DominioOperacao operacao();
}
