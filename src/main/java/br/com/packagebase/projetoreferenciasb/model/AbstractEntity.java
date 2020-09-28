package br.com.packagebase.projetoreferenciasb.model;

import br.com.packagebase.projetoreferenciasb.domain.DominioSituacaoRegistro;
import br.com.packagebase.projetoreferenciasb.utils.TraceUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Log4j2
@Data
@NoArgsConstructor
@SuperBuilder
@MappedSuperclass
public abstract class AbstractEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;

    public abstract Long getId();

    @Column(name = "id_sit", length = 1)
    private DominioSituacaoRegistro situacaoRegistro;

    @NotNull(message = "Usuário de atualização é obrigatório")
    @Column(name = "cd_usu_atu", length = 25, nullable = false)
    private String usuarioAtualizacao;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dh_atu", nullable = false)
    private Date dataHoraAtualizacao;

    @PrePersist
    private void prePersist() {
        dataHoraAtualizacao = new Date();
        situacaoRegistro = DominioSituacaoRegistro.ATIVO;
        registerDataMDC();
    }

    @PreUpdate
    private void preUpdate() {
		dataHoraAtualizacao = new Date();
        registerDataMDC();
	}

	@PreRemove
    private void preRemove() {
        dataHoraAtualizacao = new Date();
        registerDataMDC();
	}

    @PostPersist
    private void registerDataMDC(){
        Long id = this.getId();
        TraceUtils.setTransaction(TraceUtils.TRANSACTION_ID, id != null ? id.toString() : null);
    }

}
