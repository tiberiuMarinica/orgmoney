package ro.orgmoney.client.mq.listeners;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import ro.orgmoney.model.dtos.FileDto;
import ro.orgmoney.model.dtos.ReportResponseDto;
import ro.orgmoney.model.dtos.TransactionDto;
import ro.orgmoney.model.dtos.TransactionErrorDto;

@Component
public class RabbitMQListener {

	private static Logger LOGGER = LoggerFactory.getLogger(RabbitMQListener.class);

	@RabbitListener(queues = "${rabbitmq.persisted.transactions.queue}")
	public void persistedTransactionsListener(TransactionDto persistedTransactionDto) {
		LOGGER.info("Persisted transaction " + persistedTransactionDto);
	}
	
	@RabbitListener(queues = "${rabbitmq.invalid.transactions.queue}")
	public void invalidTransactionsListener(TransactionErrorDto transactionErrorDto) {
		LOGGER.info("Invalid transaction " + transactionErrorDto);
	}
	
	@RabbitListener(queues = "${rabbitmq.error.transactions.queue}")
	public void errorTransactionsListener(TransactionErrorDto transactionErrorDto) {
		LOGGER.info("Error transaction " + transactionErrorDto);
	}
	
	@RabbitListener(queues = "${rabbitmq.report.responses.queue}")
	public void reportResponsesListener(ReportResponseDto reportResponseDto) {
		LOGGER.info("Received report response for report request " + reportResponseDto.getReportRequestId().toString());
		
		FileDto fileDto = reportResponseDto.getFileDto();
		LOGGER.info(new String(fileDto.getContent()));
        
	}
}
