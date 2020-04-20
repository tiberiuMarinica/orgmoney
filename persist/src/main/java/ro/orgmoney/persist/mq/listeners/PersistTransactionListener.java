package ro.orgmoney.persist.mq.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ro.orgmoney.model.dtos.TransactionDto;
import ro.orgmoney.model.dtos.TransactionErrorDto;
import ro.orgmoney.persist.services.abs.PersistService;

@Component
public class PersistTransactionListener {

	private static Logger LOGGER = LoggerFactory.getLogger(PersistTransactionListener.class);
	
	@Autowired
	private PersistService persistService;
	
	@Value("${rabbitmq.persisted.transactions.exchange}")
	private String persistedTransactionsExchangeName;
	
	@Value("${rabbitmq.persisted.transactions.routingKey}")
	private String persistedTransactionsRoutingKey;
	
	@Value("${rabbitmq.error.transactions.exchange}")
	private String errorTransactionsExchangeName;
	
	@Value("${rabbitmq.error.transactions.routingKey}")
	private String errorTransactionsRoutingKey;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@RabbitListener(queues = "${rabbitmq.validated.transactions.queue}")
	public void persistTransaction(TransactionDto transactionDto) {
		try {
			transactionDto = persistService.create(transactionDto);
			
			rabbitTemplate.convertAndSend(persistedTransactionsExchangeName, persistedTransactionsRoutingKey, transactionDto);
		} catch(Exception e) {
			LOGGER.error(e.getMessage() + " exception while persisting transaction " + transactionDto.getCorrelationId(), e);
			
			rabbitTemplate.convertAndSend(errorTransactionsExchangeName, errorTransactionsRoutingKey, new TransactionErrorDto(e.getMessage(), transactionDto));
		}
		
	}
	
}
