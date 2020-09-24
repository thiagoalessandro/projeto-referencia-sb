package br.com.packagebase.projetoreferenciasb.model.converter;

import br.com.packagebase.projetoreferenciasb.domain.DominioSituacaoRegistro;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class SituacaoConverter implements AttributeConverter<DominioSituacaoRegistro, String> {

	@Override
	public String convertToDatabaseColumn(DominioSituacaoRegistro attribute) {
		return attribute.getDescription();
	}

	@Override
	public DominioSituacaoRegistro convertToEntityAttribute(String dbData) {
		return DominioSituacaoRegistro.convertStringToEnum(dbData);
	}

}
