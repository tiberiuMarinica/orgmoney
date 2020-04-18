package ro.orgmoney.validate.services.abs;

import ro.orgmoney.model.dtos.TransactionDto;
import ro.orgmoney.validate.exceptions.InvalidTransactionException;

public interface ValidationService {

	public abstract void validate(TransactionDto transaction) throws InvalidTransactionException;
	
}
