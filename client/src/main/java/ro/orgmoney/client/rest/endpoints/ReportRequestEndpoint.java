package ro.orgmoney.client.rest.endpoints;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ro.orgmoney.model.dtos.ReportRequestDto;
import ro.orgmoney.model.dtos.ReportRequestDto.Name;

@RestController
@RequestMapping("/reportRequest")
public class ReportRequestEndpoint {
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Value("${rabbitmq.report.requests.exchange}")
	private String reportRequestsExchangeName;
	
	@Value("${rabbitmq.report.requests.routingKey}")
	private String reportRequestsRoutingKey;
	
	@GetMapping("allTransactions")
	public void sendAllTransactionsReportRequest() {
		rabbitTemplate.convertAndSend(reportRequestsExchangeName, reportRequestsRoutingKey, new ReportRequestDto(Name.ALL_TRANSACTIONS, null));
	}
	
}
