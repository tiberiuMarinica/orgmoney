package ro.orgmoney.persist.mq.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ro.orgmoney.model.dtos.TransactionDto;
import ro.orgmoney.persist.dtos.TransactionErrorDto;
import ro.orgmoney.persist.services.abs.PersistService;

@Component
public class PersistTransactionMessageListener {

	private static Logger LOGGER = LoggerFactory.getLogger(PersistTransactionMessageListener.class);
	
	@Autowired
	private PersistService persistService;
	
	@Value("${rabbitmq.error.transactions.exchange}")
	private String errorTransactionsExchangeName;
	
	@Value("${rabbitmq.error.transactions.routingKey}")
	private String errorTransactionsRoutingKey;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	public void persistTransaction(TransactionDto transaction) {
		try {
			persistService.create(transaction);
		} catch(Exception e) {
			LOGGER.error(e.getMessage() + " exception while persisting transaction " + transaction.getCorrelationId(), e);
			
			rabbitTemplate.convertAndSend(errorTransactionsExchangeName, errorTransactionsRoutingKey, new TransactionErrorDto(e.getMessage(), transaction));
		}
		
	}
	
}
