package sdu.sem2.se17.domain.persistenceinterface;

import sdu.sem2.se17.domain.auth.User;
import sdu.sem2.se17.domain.production.Production;

import java.util.ArrayList;
import java.util.Optional;

public interface UserHandler extends GenericCrud<User> {
    Optional<User> findByUsername(String username);
}
