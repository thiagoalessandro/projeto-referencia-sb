package br.com.packagebase.projetoreferenciasb.model.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class BooleanToStringConverter implements AttributeConverter<Boolean, String> {

    @Override
    public String convertToDatabaseColumn(Boolean value) {        
        return (value != null && value) ? "S" : "N";            
        }    

    @Override
    public Boolean convertToEntityAttribute(String value) {
        return "S".equals(value);
        }
    }
