package com.xebia.innovationportal.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Categories")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String categoryName;

	@Column(length = 200)
	private String categoryDescription;

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryDescription() {
		return categoryDescription;
	}

	public void setCategoryDescription(String categoryDescription) {
		this.categoryDescription = categoryDescription;
	}

	public Integer getId() {
		return id;
	}

	public Category() {
		// TODO Auto-generated constructor stub
	}

	public Category(String categoryName, String categoryDescription) {
		super();
		this.categoryName = categoryName;
		this.categoryDescription = categoryDescription;
	}

}
