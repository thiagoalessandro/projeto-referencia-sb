package br.com.packagebase.projetoreferenciasb.repository;

import br.com.packagebase.projetoreferenciasb.model.LogTrace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogTraceRepository extends JpaRepository<LogTrace, Long> {

}
