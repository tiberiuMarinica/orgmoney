package ro.orgmoney.validate.unit;

import static org.junit.jupiter.api.Assertions.fail;

import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ro.orgmoney.model.dtos.TransactionDto;
import ro.orgmoney.model.dtos.TransactionDto.Type;
import ro.orgmoney.model.dtos.UserDto;
import ro.orgmoney.validate.exceptions.InvalidTransactionException;
import ro.orgmoney.validate.services.abs.ValidationService;

@SpringBootTest
public class ValidationServiceUnitTests {
	
	@Autowired
	private ValidationService validationService;
	
	
	private TransactionDto getBasicTransaction() {
		TransactionDto transactionDto = new TransactionDto();
		transactionDto.setCorrelationId(UUID.randomUUID().toString());
		transactionDto.setDescription("description");
		transactionDto.setSum(23.0d);
		transactionDto.setType(Type.IBAN_TO_IBAN);
		
		UserDto payer = new UserDto();
		payer.setCNP("1911006193764");
		payer.setIBAN("RO24RZBR7392862222335463");
		payer.setName("Ion Ionescu");
		
		transactionDto.setPayer(payer);
		
		UserDto payee = new UserDto();
		payee.setCNP("5010814266701");
		payee.setIBAN("RO07PORL2295441549592413");
		payee.setName("Ioana Mihaiescu");
		
		transactionDto.setPayee(payee);
		
		return transactionDto;
	}
	
	@Test
	public void testThrowExceptionIfTransactionNull() {
		TransactionDto dto = null;
		
		try {
			validationService.validate(dto);
			
			fail();
		} catch (InvalidTransactionException ite) {
			
			//all good
			
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void testThrowExceptionIfTransactionFieldsAreNull() {
		TransactionDto dto = new TransactionDto();
		
		try {
			validationService.validate(dto);
			
			fail();
		} catch (InvalidTransactionException ite) {
			
			//all good
			
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void testThrowExceptionIfTransactionPayeeHasInvalidName() {
		TransactionDto dto = getBasicTransaction();
		dto.getPayee().setName(null);
		
		try {
			validationService.validate(dto);
			
			fail();
		} catch (InvalidTransactionException ite) {
			
			//all good
			
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void testThrowExceptionIfTransactionPayerHasInvalidName() {
		TransactionDto dto = getBasicTransaction();
		dto.getPayer().setName(null);
		
		try {
			validationService.validate(dto);
			
			fail();
		} catch (InvalidTransactionException ite) {
			
			//all good
			
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void testThrowExceptionIfTransactionPayerHasInvalidCnp() {
		TransactionDto dto = getBasicTransaction();
		dto.getPayer().setCNP("123");
		
		try {
			validationService.validate(dto);
			
			fail();
		} catch (InvalidTransactionException ite) {
			
			//all good
			
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void testThrowExceptionIfTransactionPayeeHasInvalidCnp() {
		TransactionDto dto = getBasicTransaction();
		dto.getPayee().setCNP("123");
		
		try {
			validationService.validate(dto);
			
			fail();
		} catch (InvalidTransactionException ite) {
			
			//all good
			
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void testThrowExceptionIfIbanToIbanTransactionPayerHasInvalidIban() {
		TransactionDto dto = getBasicTransaction();
		dto.getPayer().setIBAN("abc");
		
		try {
			validationService.validate(dto);
			
			fail();
		} catch (InvalidTransactionException ite) {
			
			//all good
			
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void testThrowExceptionIfIbanToIbanTransactionPayeeHasInvalidIban() {
		TransactionDto dto = getBasicTransaction();
		dto.getPayee().setIBAN("abc");
		
		try {
			validationService.validate(dto);
			
			fail();
		} catch (InvalidTransactionException ite) {
			
			//all good
			
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void testThrowExceptionIfIbanToWalletTransactionPayerHasInvalidIban() {
		TransactionDto dto = getBasicTransaction();
		dto.setType(Type.IBAN_TO_WALLET);
		dto.getPayer().setIBAN("abc");
		
		try {
			validationService.validate(dto);
			
			fail();
		} catch (InvalidTransactionException ite) {
			
			//all good
			
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void testNoExceptionIfIbanToWalletTransactionPayeeHasInvalidIban() {
		TransactionDto dto = getBasicTransaction();
		dto.setType(Type.IBAN_TO_WALLET);
		dto.getPayee().setIBAN("abc");
		
		try {
			validationService.validate(dto);
		} catch (InvalidTransactionException ite) {
			fail(ite);
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void testThrowExceptionIfWalletToIbanTransactionPayeeHasInvalidIban() {
		TransactionDto dto = getBasicTransaction();
		dto.setType(Type.WALLET_TO_IBAN);
		dto.getPayee().setIBAN("abc");
		
		try {
			validationService.validate(dto);
			
			fail();
		} catch (InvalidTransactionException ite) {
			
			//all good
			
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void testNoExceptionIfIbanToWalletTransactionPayerHasInvalidIban() {
		TransactionDto dto = getBasicTransaction();
		dto.setType(Type.IBAN_TO_WALLET);
		dto.getPayer().setIBAN("abc");
		
		try {
			validationService.validate(dto);
			
			fail();
		} catch (InvalidTransactionException ite) {
			
			//all good
			
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void testNoExceptionIfWalletToWalletTransactionPayerHasInvalidIban() {
		TransactionDto dto = getBasicTransaction();
		dto.setType(Type.WALLET_TO_WALLET);
		dto.getPayer().setIBAN("abc");
		
		try {
			validationService.validate(dto);
		} catch (InvalidTransactionException ite) {
			
			fail(ite);
		} catch (Exception e) {
			fail(e);
		}
	}
	
	@Test
	public void testNoExceptionIfWalletToWalletTransactionPayeeHasInvalidIban() {
		TransactionDto dto = getBasicTransaction();
		dto.setType(Type.WALLET_TO_WALLET);
		dto.getPayee().setIBAN("abc");
		
		try {
			validationService.validate(dto);
		} catch (InvalidTransactionException ite) {
			
			fail(ite);
		} catch (Exception e) {
			fail(e);
		}
	}
}
