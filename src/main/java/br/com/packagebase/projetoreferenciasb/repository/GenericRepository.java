package br.com.packagebase.projetoreferenciasb.repository;

import br.com.packagebase.projetoreferenciasb.domain.DominioSituacaoRegistro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID> {

    List<T> findBySituacaoRegistro(DominioSituacaoRegistro situacao);

}
