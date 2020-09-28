package br.com.packagebase.projetoreferenciasb.repository;

import br.com.packagebase.projetoreferenciasb.model.LogTransacional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface LogTransacionalRepository extends JpaRepository<LogTransacional, Long>, JpaSpecificationExecutor<LogTransacional> {

}
