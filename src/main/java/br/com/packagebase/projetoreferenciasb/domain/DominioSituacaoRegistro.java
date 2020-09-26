package br.com.packagebase.projetoreferenciasb.domain;

public enum DominioSituacaoRegistro implements IBasicDominio {

	ATIVO("A"),
	EXCLUIDO("E");
	
	private final String description;

	DominioSituacaoRegistro(String description) {
		this.description = description;
	}

	@Override
	public String getDescription() {
		return this.description;
	}

	public static DominioSituacaoRegistro convertStringToEnum(String situacao) {
		switch (situacao) {
			case "E":
				return DominioSituacaoRegistro.EXCLUIDO;
			case "A":
			default:
				return DominioSituacaoRegistro.ATIVO;
		}
	}


}
