package ro.orgmoney.persist.services.abs;

import ro.orgmoney.model.dtos.TransactionDto;

public interface PersistService {

	public abstract void create(TransactionDto transactionDto);
	
}
