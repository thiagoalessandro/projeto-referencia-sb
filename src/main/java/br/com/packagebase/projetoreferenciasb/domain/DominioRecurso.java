package br.com.packagebase.projetoreferenciasb.domain;

public enum DominioRecurso implements IBasicDominio {

	CONFIGURACAO("Configuração"),
	LOG_TRANSACIONAL("Log Transacional");

	private final String description;

	DominioRecurso(String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
	
}
