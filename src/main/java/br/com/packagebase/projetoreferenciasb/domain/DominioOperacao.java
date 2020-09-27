package br.com.packagebase.projetoreferenciasb.domain;

public enum DominioOperacao implements IBasicDominio {

	CADASTRAR("Cadastrar"),
	EDITAR("Editar"),
	CONSULTAR("Consulta"),
	EXCLUIR("Excluir");


	private final String description;

	DominioOperacao(String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}
	
}
