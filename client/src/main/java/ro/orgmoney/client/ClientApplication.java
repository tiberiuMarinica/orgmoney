package ro.orgmoney.client;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import ro.orgmoney.model.dtos.TransactionDto;
import ro.orgmoney.model.dtos.UserDto;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

}

@Component
class CommandLineAppStartupRunner implements CommandLineRunner {
 
	@Autowired
	private RabbitTemplate rabbitTemplate; 
	
	@Value("${rabbitmq.new.transactions.exchange}")
	private String newTransactionsExchangeName;
	
	@Value("${rabbitmq.new.transactions.routingKey}")
	private String newTransactionsRoutingKey;
	
    @Override
    public void run(String...args) throws Exception {
    	
    	UserDto payer = new UserDto("12345", "Ion Ion", "NL10RABO5361018961");
    	UserDto payee = new UserDto("12345", "Ion Ion", "JO34SXSN8989419175328827734816");
    	TransactionDto t = new TransactionDto(UUID.randomUUID().toString(), payer, payee, TransactionDto.Type.IBAN_TO_IBAN, 24.3, "description");
    	
    	rabbitTemplate.convertAndSend(newTransactionsExchangeName, newTransactionsRoutingKey, t);
    }
}