package ro.orgmoney.model.dtos;

import java.io.Serializable;
import java.util.UUID;

public class ReportResponseDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private UUID reportRequestId;
	private FileDto fileDto;

	public ReportResponseDto(UUID reportRequestId, FileDto fileDto) {
		super();
		this.reportRequestId = reportRequestId;
		this.fileDto = fileDto;
	}

	public UUID getReportRequestId() {
		return reportRequestId;
	}

	public void setReportRequestId(UUID reportRequestId) {
		this.reportRequestId = reportRequestId;
	}

	public FileDto getFileDto() {
		return fileDto;
	}

	public void setFileDto(FileDto fileDto) {
		this.fileDto = fileDto;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileDto == null) ? 0 : fileDto.hashCode());
		result = prime * result + ((reportRequestId == null) ? 0 : reportRequestId.hashCode());
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
		ReportResponseDto other = (ReportResponseDto) obj;
		if (fileDto == null) {
			if (other.fileDto != null)
				return false;
		} else if (!fileDto.equals(other.fileDto))
			return false;
		if (reportRequestId == null) {
			if (other.reportRequestId != null)
				return false;
		} else if (!reportRequestId.equals(other.reportRequestId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ReportResponseDto [reportRequestId=");
		builder.append(reportRequestId);
		builder.append(", fileDto=");
		builder.append(fileDto);
		builder.append("]");
		return builder.toString();
	}

}
