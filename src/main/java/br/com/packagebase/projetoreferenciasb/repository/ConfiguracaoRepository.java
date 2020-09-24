package br.com.packagebase.projetoreferenciasb.repository;

import br.com.packagebase.projetoreferenciasb.model.Configuracao;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfiguracaoRepository extends GenericRepository<Configuracao, Long> {

    Optional<Configuracao> findByNome(String nome);

}
