package ro.orgmoney.persist.entities.converters;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import ro.orgmoney.model.dtos.TransactionDto.Type;

@Converter
public class TransactionTypeConverter implements AttributeConverter<Type, Integer>{

	@Override
	public Integer convertToDatabaseColumn(Type attribute) {
		return attribute.getCode();
	}

	@Override
	public Type convertToEntityAttribute(Integer dbData) {
		return Type.fromCode(dbData);
	}

}
