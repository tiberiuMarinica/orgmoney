package ro.orgmoney.persist.entities;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import ro.orgmoney.model.dtos.TransactionDto.Type;
import ro.orgmoney.persist.entities.converters.TransactionTypeConverter;

@Entity
@Table(name = "transactions")
public class Transaction implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "id", nullable = false)
	private UUID id;

	@Column(name = "type")
	@Convert(converter = TransactionTypeConverter.class)
	private Type type;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payer_id")
	private User payer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "payee_id")
	private User payee;

	@Column(name = "sum")
	private Double sum;

	@Column(name = "description")
	private String description;

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public User getPayer() {
		return payer;
	}

	public void setPayer(User payer) {
		this.payer = payer;
	}

	public User getPayee() {
		return payee;
	}

	public void setPayee(User payee) {
		this.payee = payee;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Transaction other = (Transaction) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transaction [id=");
		builder.append(id);
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
