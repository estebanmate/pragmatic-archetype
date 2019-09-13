package org.x.pragmatic.repository;

import java.util.Optional;

import org.x.pragmatic.domain.Entity;

public interface Repository<T extends Entity<ID>, ID> {
    ID save(final T entity);
    Optional<T> findById(final ID id);
}
