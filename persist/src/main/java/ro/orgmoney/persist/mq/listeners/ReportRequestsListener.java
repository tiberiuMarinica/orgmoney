package ro.orgmoney.persist.mq.listeners;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import ro.orgmoney.model.dtos.FileDto;
import ro.orgmoney.model.dtos.ReportRequestDto;
import ro.orgmoney.model.dtos.ReportRequestDto.Name;
import ro.orgmoney.model.dtos.ReportResponseDto;
import ro.orgmoney.persist.services.abs.ReportService;

@Component
public class ReportRequestsListener {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private ReportService reportService;
	
	@Value("${rabbitmq.report.responses.exchange}")
	private String reportResponsesExchangeName;
	
	@Value("${rabbitmq.report.responses.routingKey}")
	private String reportResponsesRoutingKey;
	
	@RabbitListener(queues = "${rabbitmq.report.requests.queue}")
	public void makeReport(ReportRequestDto reportRequestDto) {
		
		if(Name.ALL_TRANSACTIONS.equals(reportRequestDto.getName())) {
			FileDto fileDto = reportService.getAllTransactionsReport();
			
			rabbitTemplate.convertAndSend(reportResponsesExchangeName, reportResponsesRoutingKey, new ReportResponseDto(reportRequestDto.getId(), fileDto));
		}
		
	}
	
}
