package com.xebia.innovationportal.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Setter;

@Setter
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String email;

	@Column(name = "enabled")
	private boolean isEnabled;

	@Column(nullable = false)
	private String name;

	private String displayName;

	private String location;

	private String timezone;

	private String password;

	@CreatedDate
	private LocalDateTime creationDate;

	@Column(nullable = false)
	private String employeeCode;

	private String designation;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "subcategory_id")
	private SubCategory subCategory;

	public User() {
	}

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(joinColumns = { @JoinColumn(name = "user_id") }, inverseJoinColumns = {
			@JoinColumn(name = "authority_id") })
	private Set<Authority> authorities;
	@OneToMany(mappedBy = "user")
	private List<Idea> idea;

	private User(Builder builder) {
		email = builder.email;
		isEnabled = builder.isEnabled;
		name = builder.name;
		displayName = builder.displayName;
		location = builder.location;
		timezone = builder.timezone;
		password = builder.password;
		creationDate = builder.creationDate;
		employeeCode = builder.employeeCode;
		designation = builder.designation;
		authorities = builder.authorities;
		subCategory = builder.subCategory;
	}

	public void updateStatus(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public void updateTimeZone(final String timezone) {
		this.timezone = timezone;
	}

	public void updateCreationDate() {
		this.creationDate = LocalDateTime.now();
	}

	public void updateAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public void updateSubCategory(SubCategory subCategory) {
		this.subCategory = subCategory;
	}

	public Long getId() {
		return id;
	}

	public SubCategory getSubCategory() {
		return subCategory;
	}

	public String getEmail() {
		return email;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void updateEnabled(final boolean enabled) {
		this.isEnabled = enabled;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getLocation() {
		return location;
	}

	public String getTimezone() {
		return timezone;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public String getEmployeeCode() {
		return employeeCode;
	}

	public String getDesignation() {
		return designation;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public String getPassword() {
		return password;
	}

	public static class Builder {

		private String email;
		private boolean isEnabled;
		private String name;
		private String displayName;
		private String location;
		private String timezone;
		private String password;
		private LocalDateTime creationDate;
		private String employeeCode;
		private String designation;
		private Set<Authority> authorities;
		private SubCategory subCategory;

		public Builder(final SubCategory subCategory) {
			this.subCategory = subCategory;
		}

		public Builder(final String email) {
			this.email = email;
		}

		public Builder enabled(final boolean isEnabled) {
			this.isEnabled = isEnabled;
			return this;
		}

		public Builder name(final String name) {
			this.name = name;
			return this;
		}

		public void updateEnabled(final boolean enabled) {
			this.isEnabled = enabled;
		}

		public Builder displayName(final String displayName) {
			this.displayName = displayName;
			return this;
		}

		public Builder location(final String location) {
			this.location = location;
			return this;
		}

		public Builder timezone(final String timezone) {
			this.timezone = timezone;
			return this;
		}

		public Builder password(final String password) {
			this.password = password;
			return this;
		}

		public Builder creationDate(final LocalDateTime creationDate) {
			this.creationDate = creationDate;
			return this;
		}

		public Builder employeeCode(final String employeeCode) {
			this.employeeCode = employeeCode;
			return this;
		}

		public Builder designation(final String designation) {
			this.designation = designation;
			return this;
		}

		public Builder authorities(final Set<Authority> authorities) {
			this.authorities = authorities;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", isEnabled=" + isEnabled + ", name=" + name + ", displayName="
				+ displayName + ", location=" + location + ", timezone=" + timezone + ", password=" + password
				+ ", creationDate=" + creationDate + ", employeeCode=" + employeeCode + ", designation=" + designation
				+ ", authorities=" + authorities + "]";
	}

}
