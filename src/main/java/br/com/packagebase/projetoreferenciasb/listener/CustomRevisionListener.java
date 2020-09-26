package br.com.packagebase.projetoreferenciasb.listener;

import br.com.packagebase.projetoreferenciasb.model.Auditoria;
import br.com.packagebase.projetoreferenciasb.utils.HttpReqRespUtils;
import org.hibernate.envers.RevisionListener;
import org.springframework.stereotype.Component;

@Component
public class CustomRevisionListener implements RevisionListener {

    @Override
    public void newRevision(Object revisionEntity) {
        Auditoria auditoria = (Auditoria) revisionEntity;
        auditoria.setIp(HttpReqRespUtils.getClientIpAddressIfServletRequestExist());

        //TODO use SecurityContextHolder.getContext().getAuthentication() when security exists
        auditoria.setCodigoUsuarioAtualizacao(auditoria.getCodigoUsuarioAtualizacao());
    }

}
