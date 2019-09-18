package it.pkg.repository;

import java.util.Optional;

import it.pkg.domain.Entity;

public interface Repository<T extends Entity<ID>, ID> {
    ID save(final T entity);
    Optional<T> findById(final ID id);
}
