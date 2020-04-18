package ro.orgmoney.model.dtos;

import java.io.Serializable;

/**
 * Should be in a separate .jar in a repository, but for the sake of simplicity,
 * i just copied them between projects
 *
 */

public class UserDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String CNP;
	private String name;
	private String IBAN;
	
	public UserDto(String CNP, String name, String IBAN) {
		super();
		this.CNP = CNP;
		this.name = name;
		this.IBAN = IBAN;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIBAN() {
		return IBAN;
	}

	public void setIBAN(String iBAN) {
		IBAN = iBAN;
	}

	public String getCNP() {
		return CNP;
	}

	public void setCNP(String cNP) {
		CNP = cNP;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((CNP == null) ? 0 : CNP.hashCode());
		result = prime * result + ((IBAN == null) ? 0 : IBAN.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		UserDto other = (UserDto) obj;
		if (CNP == null) {
			if (other.CNP != null)
				return false;
		} else if (!CNP.equals(other.CNP))
			return false;
		if (IBAN == null) {
			if (other.IBAN != null)
				return false;
		} else if (!IBAN.equals(other.IBAN))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("User [CNP=");
		builder.append(CNP);
		builder.append(", name=");
		builder.append(name);
		builder.append(", IBAN=");
		builder.append(IBAN);
		builder.append("]");
		return builder.toString();
	}

}
