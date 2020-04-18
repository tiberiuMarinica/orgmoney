package ro.orgmoney.persist.configs;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ro.orgmoney.persist.mq.listeners.PersistTransactionMessageListener;

@Configuration
public class RabbitMQConfig {

	@Value("${rabbitmq.validated.transactions.queue}")
	private String validatedTransactionsQueueName;

	@Value("${rabbitmq.persisted.transactions.exchange}")
	private String persistedTransactionsExchangeName;

	@Value("${rabbitmq.error.transactions.exchange}")
	private String errorTransactionsExchangeName;
	
	@Bean
	public Queue validatedTransactionsQueueName() {
		return new Queue(validatedTransactionsQueueName);
	}

	@Bean
	public DirectExchange persistedTransactionsExchangeName() {
		return new DirectExchange(persistedTransactionsExchangeName);
	}

	@Bean
	public DirectExchange invalidTransactionsExchange() {
		return new DirectExchange(errorTransactionsExchangeName);
	}
	
	@Bean
	public MessageListenerAdapter listenerAdapter(PersistTransactionMessageListener listener) {
		return new MessageListenerAdapter(listener, "persistTransaction");
	}
	@Bean
	public SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueueNames(validatedTransactionsQueueName);
		container.setMessageListener(listenerAdapter);
		return container;
	}
}
