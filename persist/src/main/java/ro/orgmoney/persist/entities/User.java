package ro.orgmoney.persist.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "cnp", unique = true, nullable = false)
	private String CNP;
	
	@Column(name = "iban", nullable = false)
	private String IBAN;
	
	@OneToMany(mappedBy = "payer_id")
	private List<Transaction> transactionsAsPayer;

	@OneToMany(mappedBy = "payee_id")
	private List<Transaction> transactionsAsPayee;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCNP() {
		return CNP;
	}

	public void setCNP(String cNP) {
		CNP = cNP;
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public List<Transaction> getTransactionsAsPayer() {
		return transactionsAsPayer;
	}

	public void setTransactionsAsPayer(List<Transaction> transactionsAsPayer) {
		this.transactionsAsPayer = transactionsAsPayer;
	}

	public List<Transaction> getTransactionsAsPayee() {
		return transactionsAsPayee;
	}

	public void setTransactionsAsPayee(List<Transaction> transactionsAsPayee) {
		this.transactionsAsPayee = transactionsAsPayee;
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
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
