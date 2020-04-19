package ro.orgmoney.client.rest.endpoints;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.orgmoney.model.dtos.TransactionDto;

@RestController
@RequestMapping("/transaction")
public class AddTransactionEndpoint {

	@Autowired
	private RabbitTemplate rabbitTemplate; 
	
	@Value("${rabbitmq.new.transactions.exchange}")
	private String newTransactionsExchangeName;
	
	@Value("${rabbitmq.new.transactions.routingKey}")
	private String newTransactionsRoutingKey;
	
	@PostMapping("")
	public void addNewTransaction(@RequestBody TransactionDto transactionDto) {
		transactionDto.setCorrelationId(UUID.randomUUID().toString());
		
    	rabbitTemplate.convertAndSend(newTransactionsExchangeName, newTransactionsRoutingKey, transactionDto);
	}
	
}
