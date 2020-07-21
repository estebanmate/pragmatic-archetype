#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.repository.inmemory;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import ${package}.domain.Entity;
import ${package}.repository.Repository;

abstract class AbstractInMemoryRepository<T extends Entity<ID>, ID> implements Repository<T, ID> {
    final Map<ID, T> entities;

    AbstractInMemoryRepository() {
        entities = new ConcurrentHashMap<>();
    }

    @Override
    public ID save(final T entity) {
        var id = entity.getId();
        if (entity.getId() == null) {
            id = getRandomId();
            entities.put(id, entity);
            entity.identifyBy(id);
            return id;
        }
        entities.put(id, entity);
        return id;
    }

    @Override
    public Optional<T> findById(final ID id) {
        Objects.requireNonNull(id, "id must be null");
        T value = entities.get(id);
        if (value == null) {
            return Optional.empty();
        }
        return Optional.of(value);
    }
}