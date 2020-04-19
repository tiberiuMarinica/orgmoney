package ro.orgmoney.model.dtos;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

public class ReportRequestDto implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum Name {
		ALL_TRANSACTIONS
	}

	private UUID id;
	private Name name;
	private Map<String, String> parameters;

	public ReportRequestDto(Name name, Map<String, String> parameters) {
		super();
		this.name = name;
		this.parameters = parameters;
		this.id = UUID.randomUUID();

	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public Map<String, String> getParameters() {
		return parameters;
	}

	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
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
		ReportRequestDto other = (ReportRequestDto) obj;
		if (name != other.name)
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportRequestDto [name=");
		builder.append(name);
		builder.append(", parameters=");
		builder.append(parameters);
		builder.append("]");
		return builder.toString();
	}

}
