package ro.orgmoney.persist.services.abs;

import ro.orgmoney.model.dtos.FileDto;

public interface ReportService {

	public abstract FileDto getAllTransactionsReport();

}
