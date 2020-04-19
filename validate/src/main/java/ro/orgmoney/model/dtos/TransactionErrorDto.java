package ro.orgmoney.model.dtos;

import java.io.Serializable;

public class TransactionErrorDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String errorMessage;
	private TransactionDto transaction;

	public TransactionErrorDto(String errorMessage, TransactionDto transaction) {
		super();
		this.errorMessage = errorMessage;
		this.transaction = transaction;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public TransactionDto getTransaction() {
		return transaction;
	}

	public void setTransaction(TransactionDto transaction) {
		this.transaction = transaction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((errorMessage == null) ? 0 : errorMessage.hashCode());
		result = prime * result + ((transaction == null) ? 0 : transaction.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TransactionErrorDto other = (TransactionErrorDto) obj;
		if (errorMessage == null) {
			if (other.errorMessage != null)
				return false;
		} else if (!errorMessage.equals(other.errorMessage))
			return false;
		if (transaction == null) {
			if (other.transaction != null)
				return false;
		} else if (!transaction.equals(other.transaction))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "InvalidTransactionDto [errorMessage=" + errorMessage + ", transaction=" + transaction + "]";
	}

}
