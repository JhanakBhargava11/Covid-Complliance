package com.xebia.innovationportal.utils;

import static org.springframework.web.util.UriComponentsBuilder.fromUri;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkRelation;
import org.springframework.hateoas.UriTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@Component("pageAssembler")
public class PaginatedResourceAssembler<T> {

	private final HateoasPageableHandlerMethodArgumentResolver pageableResolver;

	private boolean forceFirstAndLastRels = false;

	public PaginatedResourceAssembler(@Nullable HateoasPageableHandlerMethodArgumentResolver resolver) {
		this.pageableResolver = resolver == null ? new HateoasPageableHandlerMethodArgumentResolver() : resolver;
	}

	public void setForceFirstAndLastRels(boolean forceFirstAndLastRels) {
		this.forceFirstAndLastRels = forceFirstAndLastRels;
	}

	public PaginatedResource<T> assemble(final List<T> content, final Pageable pageable, final long totalRecords) {
		return assemble(new PageImpl<T>(content, pageable, totalRecords));
	}

	public PaginatedResource<T> assemble(final Page<T> page) {

		Assert.notNull(page, "Page must not be null!");
		Assert.notNull(page.getContent(), "Page Content must not be null!");
		Assert.notNull(page.getPageable(), "Pageable must not be null!");

		PaginatedResource<T> paginatedResource = new PaginatedResource<>(page);
		addPaginationLinks(paginatedResource, page);
		return paginatedResource;
	}

	private void addPaginationLinks(final PaginatedResource<T> paginatedResource, final Page<T> page) {

		UriTemplate base = getUriTemplate();

		boolean isNavigable = page.hasPrevious() || page.hasNext();

		Link selfLink = createLink(base, page.getPageable(), IanaLinkRelations.SELF);

		paginatedResource.getMetadata().add(selfLink);

		if (page.hasPrevious()) {
			paginatedResource.getMetadata().add(createLink(base, page.previousPageable(), IanaLinkRelations.PREV));
		}

		if (page.hasNext()) {
			paginatedResource.getMetadata().add(createLink(base, page.nextPageable(), IanaLinkRelations.NEXT));
		}

		if (isNavigable || forceFirstAndLastRels) {
			paginatedResource.getMetadata()
					.add(createLink(base, PageRequest.of(0, page.getSize(), page.getSort()), IanaLinkRelations.FIRST));
		}

		if (isNavigable || forceFirstAndLastRels) {

			int lastIndex = page.getTotalPages() == 0 ? 0 : page.getTotalPages() - 1;

			paginatedResource.getMetadata().add(createLink(base,
					PageRequest.of(lastIndex, page.getSize(), page.getSort()), IanaLinkRelations.LAST));
		}
	}

	private Link createLink(UriTemplate base, Pageable pageable, LinkRelation relation) {

		UriComponentsBuilder builder = fromUri(base.expand());
		this.pageableResolver.enhance(builder, null, pageable);

		return new Link(UriTemplate.of(builder.build().toString()), relation);
	}

	private UriTemplate getUriTemplate() {
		return UriTemplate.of(currentRequest());
	}

	private static String currentRequest() {
		return ServletUriComponentsBuilder.fromCurrentRequest().build().toString();
	}
}