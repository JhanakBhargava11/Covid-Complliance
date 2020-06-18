package com.xebia.innovationportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users_authorities")
public class UsersAuthorities {

	@Id
	@Column(name = "user_id")
	private Long userId;
	@Column(name = "authority_id")
	private Integer authorityId;

}
