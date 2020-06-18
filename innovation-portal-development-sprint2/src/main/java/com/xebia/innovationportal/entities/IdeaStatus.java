package com.xebia.innovationportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "IDEA_STATUS")
public class IdeaStatus {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false)
	private String status;

	private String statusDescription;

	public Integer getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	public IdeaStatus() {
	}

	public IdeaStatus(String status, String statusDescription) {
		this.status = status;
		this.statusDescription = statusDescription;
	}
}
