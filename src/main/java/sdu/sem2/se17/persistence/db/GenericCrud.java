package sdu.sem2.se17.persistence.db;

import java.util.Optional;

public interface GenericCrud {
    Optional<Object[]> create(Object[] objects);
    Optional<Object[]> read(long id);
    void update(Object[] objects);
    void delete(long id);
}
