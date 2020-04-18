package ro.orgmoney.validate.mq.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ro.orgmoney.model.dtos.TransactionDto;
import ro.orgmoney.validate.dtos.TransactionErrorDto;
import ro.orgmoney.validate.exceptions.InvalidTransactionException;
import ro.orgmoney.validate.services.abs.ValidationService;

@Component
public class ValidateTransactionMessageListener {

	private static Logger LOGGER = LoggerFactory.getLogger(ValidateTransactionMessageListener.class);
	
	@Autowired
	private ValidationService validationService;
	
	@Value("${rabbitmq.invalid.transactions.exchange}")
	private String invalidTransactionsExchangeName;
	
	@Value("${rabbitmq.invalid.transactions.routingKey}")
	private String invalidTransactionsRoutingKey;
	
	@Value("${rabbitmq.error.transactions.exchange}")
	private String errorTransactionsExchangeName;
	
	@Value("${rabbitmq.error.transactions.routingKey}")
	private String errorTransactionsRoutingKey;
	
	@Value("${rabbitmq.validated.transactions.exchange}")
	private String validatedTransactionsExchangeName;
	
	@Value("${rabbitmq.validated.transactions.routingKey}")
	private String validatedTransactionRoutingKey;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void validateTransaction(TransactionDto transaction) {
		try {
			
			validationService.validate(transaction);
			
			LOGGER.info("Transaction " + transaction.getCorrelationId() + " is valid!");
			
			rabbitTemplate.convertAndSend(validatedTransactionsExchangeName, validatedTransactionRoutingKey, transaction);
			
		} catch(InvalidTransactionException ite) {
			LOGGER.error(ite.getMessage() + " exception while validating transaction " + transaction.getCorrelationId(), ite);
		
			rabbitTemplate.convertAndSend(invalidTransactionsExchangeName, invalidTransactionsRoutingKey, new TransactionErrorDto(ite.getMessage(), transaction));
		}catch (Exception e) {
			LOGGER.error(e.getMessage() + " exception while validating transaction " + transaction.getCorrelationId(), e);
			
			rabbitTemplate.convertAndSend(errorTransactionsExchangeName, errorTransactionsRoutingKey, new TransactionErrorDto(e.getMessage(), transaction));
		}
		
	}
	
}
