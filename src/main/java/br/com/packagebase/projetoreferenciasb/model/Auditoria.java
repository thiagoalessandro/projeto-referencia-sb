package br.com.packagebase.projetoreferenciasb.model;

import br.com.packagebase.projetoreferenciasb.listener.CustomRevisionListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "tbl_auditoria")
@RevisionEntity(CustomRevisionListener.class)
public class Auditoria extends DefaultRevisionEntity {

    private static final long serialVersionUID = 1L;

    @Column(name = "ip", length = 15)
    public String ip;

    @Column(name = "cd_usu_atu", length = 15)
    public String codigoUsuarioAtualizacao;
}

