
package com.xebia.innovationportal.enums;

public enum Role {

	ROLE_USER("Employee"), ROLE_ADMIN("Admin"), ROLE_MANAGER("Manager");

	private final String description;

	private Role(final String description) {
		this.description = description;
	}

	public String description() {
		return this.description;

	}
}
