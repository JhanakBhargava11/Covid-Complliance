package com.xebia.innovationportal.repositories;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public interface JPA {

    public <T> Optional<T> getSingleResultSafely(final CriteriaQuery<T> criteriaQuery);

    public <T> Optional<T> getSingleResultSafely(final TypedQuery<T> typedQuery);

    public <T> Optional<T> getSingleResultSafely(final Query query);

    public <T> List<T> getUnmodifiableResultList(final CriteriaQuery<T> query);

    public <T> List<T> getUnmodifiableResultList(final TypedQuery<T> query);

    public <T> T persist(final T entity);

    public <T, P> Optional<T> findByIdSafely(final Class<T> clazz, final P primaryKey);

    public <T, P> T getReference(final Class<T> clazz, final P primaryKey);

    public EntityManager entityManager();

    public CriteriaBuilder criteriaBuilder();

    public <T> CriteriaQuery<T> criteriaQuery(final Class<T> clazz);

    public <T> TypedQuery<T> typedQuery(CriteriaQuery<T> criteriaQuery);

}
