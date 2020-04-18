package ro.orgmoney.client.configs;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

	@Value("${rabbitmq.new.transactions.exchange}")
	private String newTransactionsExchangeName;
	
	@Bean
	public DirectExchange errorTransactionsExchange() {
		return new DirectExchange(newTransactionsExchangeName);
	}
	

}
