package ro.orgmoney.model.dtos;

import java.io.Serializable;

/**
 * Should be in a separate .jar in a repository, but for the sake of simplicity, i just copied them between projects
 *
 */

public class TransactionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Type {
		IBAN_TO_IBAN, IBAN_TO_WALLET, WALLET_TO_IBAN, WALLET_TO_WALLET
	}

	private String correlationId;
	private UserDto payer;
	private UserDto payee;
	private Type type;
	private Double sum;
	private String description;

	public String getCorrelationId() {
		return correlationId;
	}

	public void setCorrelationId(String correlationId) {
		this.correlationId = correlationId;
	}

	public UserDto getPayer() {
		return payer;
	}

	public void setPayer(UserDto payer) {
		this.payer = payer;
	}

	public UserDto getPayee() {
		return payee;
	}

	public void setPayee(UserDto payee) {
		this.payee = payee;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((correlationId == null) ? 0 : correlationId.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((payee == null) ? 0 : payee.hashCode());
		result = prime * result + ((payer == null) ? 0 : payer.hashCode());
		result = prime * result + ((sum == null) ? 0 : sum.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		TransactionDto other = (TransactionDto) obj;
		if (correlationId == null) {
			if (other.correlationId != null)
				return false;
		} else if (!correlationId.equals(other.correlationId))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (payee == null) {
			if (other.payee != null)
				return false;
		} else if (!payee.equals(other.payee))
			return false;
		if (payer == null) {
			if (other.payer != null)
				return false;
		} else if (!payer.equals(other.payer))
			return false;
		if (sum == null) {
			if (other.sum != null)
				return false;
		} else if (!sum.equals(other.sum))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transaction [correlationId=");
		builder.append(correlationId);
		builder.append(", payer=");
		builder.append(payer);
		builder.append(", payee=");
		builder.append(payee);
		builder.append(", type=");
		builder.append(type);
		builder.append(", sum=");
		builder.append(sum);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}

}
