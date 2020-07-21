#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository;

import java.util.Optional;

import ${package}.domain.Entity;

public interface Repository<T extends Entity<ID>, ID> {
    ID save(final T entity);
    Optional<T> findById(final ID id);
    ID getRandomId();
}