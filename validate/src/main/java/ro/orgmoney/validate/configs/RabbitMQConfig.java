package ro.orgmoney.validate.configs;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ro.orgmoney.validate.mq.listeners.ValidateTransactionMessageListener;

@Configuration
public class RabbitMQConfig {

	@Value("${rabbitmq.new.transactions.queue}")
	private String newTransactionsQueueName;

	@Value("${rabbitmq.validated.transactions.exchange}")
	private String validatedTransactionsExchangeName;

	@Value("${rabbitmq.invalid.transactions.exchange}")
	private String invalidTransactionsExchangeName;
	
	@Value("${rabbitmq.error.transactions.exchange}")
	private String errorTransactionsExchangeName;
	
	@Bean
	public Queue newTransactionsQueue() {
		return new Queue(newTransactionsQueueName);
	}

	@Bean
	public DirectExchange validatedTransactionsExchange() {
		return new DirectExchange(validatedTransactionsExchangeName);
	}

	@Bean
	public DirectExchange invalidTransactionsExchange() {
		return new DirectExchange(invalidTransactionsExchangeName);
	}
	
	@Bean
	public DirectExchange errorTransactionsExchange() {
		return new DirectExchange(errorTransactionsExchangeName);
	}
	
	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(newTransactionsQueueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}

	@Bean
	public MessageListenerAdapter listenerAdapter(ValidateTransactionMessageListener listener) {
		return new MessageListenerAdapter(listener, "validateTransaction");
	}

}
