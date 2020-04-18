package ro.orgmoney.validate.services.impl;

import org.iban4j.IbanUtil;
import org.springframework.stereotype.Service;

import ro.orgmoney.model.dtos.TransactionDto;
import ro.orgmoney.model.dtos.UserDto;
import ro.orgmoney.validate.exceptions.InvalidTransactionException;
import ro.orgmoney.validate.services.abs.ValidationService;

@Service
public class ValidationServiceImpl implements ValidationService {

	@Override
	public void validate(TransactionDto transaction) throws InvalidTransactionException {
		
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
		
		UserDto payer = transaction.getPayer();
		UserDto payee = transaction.getPayee();
		
		Boolean isPayerValid = isUserValid(payer);
		if(!isPayerValid) {
			throw new InvalidTransactionException("Payer is not valid for transaction " + transaction.getCorrelationId());
		}
		
		Boolean isPayeeValid = isUserValid(payee);
		if(!isPayeeValid) {
			throw new InvalidTransactionException("Payee is not valid for transaction " + transaction.getCorrelationId());
		}
		
		validateIbans(transaction);
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
	
	private Boolean isUserValid(UserDto user) {
		Boolean cnpValid = isCnpValid(user.getCNP());
		
		Boolean nameValid = false;
		if(user.getName() != null && !user.getName().trim().isEmpty()) {
			nameValid = true;
		}
		
		return cnpValid && nameValid;
	}
	
	private Boolean isCnpValid(String cnp) {
		return true;
	}
	
}
