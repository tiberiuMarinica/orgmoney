package ro.orgmoney.model.dtos;

import java.io.Serializable;

/**
 * Should be in a separate .jar in a repository, but for the sake of simplicity,
 * i just copied them between projects
 *
 */

public class TransactionDto implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Type {
		IBAN_TO_IBAN("Iban to Iban", 0), 
		IBAN_TO_WALLET("Iban to Wallet", 1), 
		WALLET_TO_IBAN("Wallet to Iban", 2),
		WALLET_TO_WALLET("Wallet to Wallet", 3);

		private String name;
		private Integer code;

		private Type(String name, Integer code) {
			this.name = name;
			this.code = code;
		}

		public String getName() {
			return this.name;
		}

		public Integer getCode() {
			return this.code;
		}

		public static Type fromCode(Integer code) {
			for (Type t : Type.values()) {
				if (t.getCode().equals(code)) {
					return t;
				}
			}

			throw new IllegalArgumentException("No Type with code=" + code + " found!");
		}
	}

	private String id;
	private String correlationId;
	private UserDto payer;
	private UserDto payee;
	private Type type;
	private Double sum;
	private String description;

	public TransactionDto(String id, String correlationId, UserDto payer, UserDto payee, Type type, Double sum,
			String description) {
		super();
		this.id = id;
		this.correlationId = correlationId;
		this.payer = payer;
		this.payee = payee;
		this.type = type;
		this.sum = sum;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
		builder.append("TransactionDto [id=");
		builder.append(id);
		builder.append(", correlationId=");
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
