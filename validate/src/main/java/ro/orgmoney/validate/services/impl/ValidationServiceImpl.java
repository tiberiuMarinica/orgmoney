package ro.orgmoney.validate.services.impl;

import org.iban4j.IbanUtil;
import org.springframework.stereotype.Service;

import ro.orgmoney.model.dtos.TransactionDto;
import ro.orgmoney.model.dtos.UserDto;
import ro.orgmoney.validate.exceptions.InvalidTransactionException;
import ro.orgmoney.validate.services.abs.ValidationService;
import ro.orgmoney.validate.utils.CnpValidator;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Override
	public void validate(TransactionDto transaction) throws InvalidTransactionException {
		
		validateNotNull(transaction);
		
		UserDto payer = transaction.getPayer();
		UserDto payee = transaction.getPayee();
		
		if(payer.getName() == null || payer.getName().trim().isEmpty()) {
			throw new InvalidTransactionException("Payer name is not valid vor transaction " + transaction.getCorrelationId());
		}
		
		if(!CnpValidator.isCnpValid(payer.getCNP())) {
			throw new InvalidTransactionException("Payer CNP is not valid vor transaction " + transaction.getCorrelationId());
		}
		
		if(payee.getName() == null || payee.getName().trim().isEmpty()) {
			throw new InvalidTransactionException("Payee name is not valid vor transaction " + transaction.getCorrelationId());
		}
		
		if(!CnpValidator.isCnpValid(payee.getCNP())) {
			throw new InvalidTransactionException("Payee CNP is not valid vor transaction " + transaction.getCorrelationId());
		}
		
		validateIbans(transaction);
	}

	private void validateNotNull(TransactionDto transaction) throws InvalidTransactionException {
		if(transaction == null) {
			throw new InvalidTransactionException("Transaction is null!");
		}
		
		if(transaction.getType() == null) {
			throw new InvalidTransactionException("Type is null for transaction " + transaction.getCorrelationId());
		}
		
		if(transaction.getPayer() == null) {
			throw new InvalidTransactionException("Payer is null for transaction " + transaction.getCorrelationId());
		}
		
		if(transaction.getPayee() == null) {
			throw new InvalidTransactionException("Paye is null for transaction " + transaction.getCorrelationId());
		}
		
		if(transaction.getSum() == null) {
			throw new InvalidTransactionException("Sum is null for transaction " + transaction.getCorrelationId());
		}
		
		if(transaction.getDescription() == null || transaction.getDescription().trim().isEmpty()) {
			throw new InvalidTransactionException("Description is null or empty for transaction " + transaction.getCorrelationId());
		}
	}

	private void validateIbans(TransactionDto transaction) throws InvalidTransactionException {
		
		UserDto payer = transaction.getPayer();
		UserDto payee = transaction.getPayee();
		
		switch (transaction.getType()) {
			case IBAN_TO_IBAN:
				
				try {
					IbanUtil.validate(payer.getIBAN());
				} catch (Exception e) {
					throw new InvalidTransactionException("Payer IBAN is invalid for transaction " + transaction.getCorrelationId());
				}
				
				try {
					IbanUtil.validate(payee.getIBAN());
				} catch (Exception e) {
					throw new InvalidTransactionException("Payee IBAN is invalid for transaction " + transaction.getCorrelationId());
				}
				
				break;
			case IBAN_TO_WALLET:
				
				try {
					IbanUtil.validate(payer.getIBAN());
				} catch (Exception e) {
					throw new InvalidTransactionException("Payer IBAN is invalid for transaction " + transaction.getCorrelationId());
				}
				
				break;
			case WALLET_TO_IBAN:
				
				try {
					IbanUtil.validate(payee.getIBAN());
				} catch (Exception e) {
					throw new InvalidTransactionException("Payee IBAN is invalid for transaction " + transaction.getCorrelationId());
				}
				
				break;
				
			case WALLET_TO_WALLET:
				
				break;
				
			default:
				throw new InvalidTransactionException("Transaction type " + transaction.getType() + " is not supported!");
		}
	}
	
}
