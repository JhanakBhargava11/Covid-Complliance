package com.xebia.innovationportal.utils;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class PaginationData extends RepresentationModel<PaginationData> {

	
	@Setter(value = AccessLevel.NONE)
	private int currentPage;

	
	@Setter(value = AccessLevel.NONE)
	private int pageSize;

	@Setter(value = AccessLevel.NONE)
	@JsonIgnore
	private Sort sort;

	
	@Setter(value = AccessLevel.NONE)
	private int totalPages;

	
	@Setter(value = AccessLevel.NONE)
	private long totalRecords;

	private PaginationData(final int currentPage, final int pageSize, final long totalRecords, final Sort sort) {
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalRecords = totalRecords;
		this.totalPages = this.pageSize == 0 ? 1 : (int) Math.ceil((double) this.totalRecords / (double) this.pageSize);
		this.sort = sort;
	}

	public static PaginationData of(final Pageable pageable, final long totalRecords) {
		return new PaginationData(pageable.getPageNumber(), pageable.getPageSize(), totalRecords, pageable.getSort());
	}


	@JsonProperty("isFirst")
	public boolean isFirst() {
		return !hasPrevious();
	}


	@JsonProperty("isLast")
	public boolean isLast() {
		return !hasNext();
	}

	
	@JsonProperty("hasNext")
	public boolean hasNext() {
		return getCurrentPage() + 1 < getTotalPages();
	}

	
	@JsonProperty("hasPrevious")
	public boolean hasPrevious() {
		return getCurrentPage() > 0;
	}

	public int queryFirstResult() {
		return this.getCurrentPage() * this.getPageSize();
	}

	public int queryMaxResults() {
		return this.getPageSize();
	}

	
	@JsonProperty("header")
	@Override
	public String toString() {
		return String.format("Page %s of %d", getCurrentPage() + 1, getTotalPages());
	}
}
