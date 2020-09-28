package br.com.packagebase.projetoreferenciasb.listener;

import br.com.packagebase.projetoreferenciasb.model.Auditoria;
import br.com.packagebase.projetoreferenciasb.utils.HttpReqRespUtils;
import br.com.packagebase.projetoreferenciasb.utils.TraceUtils;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        Auditoria auditoria = (Auditoria) revisionEntity;
        auditoria.setIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());

        //TODO: Capturar usuário do SpringSecurity quando existir camada de segurança
        auditoria.setCodigoUsuarioAtualizacao(auditoria.getCodigoUsuarioAtualizacao());
    }

}
