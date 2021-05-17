package sdu.sem2.se17.domain.persistenceinterface;

import java.util.Optional;

/*
Hampus Fink
 */
public interface GenericCrud<T> {
    Optional<T> create(T t);
    Optional<T> read(long id);
    void update(T t);
    void delete(long id);
}
