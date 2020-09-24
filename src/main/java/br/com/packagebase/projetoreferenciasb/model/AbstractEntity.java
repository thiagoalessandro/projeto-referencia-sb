package br.com.packagebase.projetoreferenciasb.model;

import br.com.packagebase.projetoreferenciasb.domain.DominioRecurso;
import br.com.packagebase.projetoreferenciasb.domain.DominioSituacaoRegistro;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Log4j2
@Data
@MappedSuperclass
public abstract class AbstractEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

    @Column(name = "id_sit", length = 1)
    private DominioSituacaoRegistro situacaoRegistro;

    @NotNull(message = "Usuário de atualização é obrigatório")
    @Column(name = "cd_usu_atu", length = 25, nullable = false)
    private String usuarioAtualizacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dh_atu", nullable = false)
    private Date dataHoraAtualizacao;

    public abstract DominioRecurso getDominioRecurso();

    @PrePersist
    public void prePersist() {
        dataHoraAtualizacao = new Date();
        situacaoRegistro = DominioSituacaoRegistro.ATIVO;
    }

    @PreUpdate
    public void preUpdate() {
		dataHoraAtualizacao = new Date();
	}

	@PreRemove
	public void preRemove() {
		dataHoraAtualizacao = new Date();
	}

	public abstract Long getId();

}
