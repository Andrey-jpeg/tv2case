package sdu.sem2.se17.domain.persistenceinterface;

import sdu.sem2.se17.domain.auth.User;
import sdu.sem2.se17.domain.credit.Credit;
import sdu.sem2.se17.domain.credit.Participant;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserHandlerImplSample implements UserHandler {
    private final ArrayList<User> users = new ArrayList<>();

    private long userIdCounter = 1;

    @Override
    public Optional<User> create(User user) {

        user.setId(userIdCounter++);

        var newUser = new User(){{
            setId(user.getId());
            setEmail(user.getEmail());
            setPassword(user.getPassword());
            setUsername(user.getUsername());
        }};


        users.add(newUser);

        return Optional.of(newUser);
    }

    @Override
    public Optional<User> read(long id) {
        return users
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst();
    }

    @Override
    public void update(User user) {
        var storedUser = users
                .stream()
                .filter(c -> c.getId() == user.getId())
                .findFirst();

        storedUser.get().setUsername(user.getUsername());
        storedUser.get().setPassword(user.getPassword());
        storedUser.get().setEmail(user.getEmail());
    }

    @Override
    public void delete(long id) {
        users.removeIf(c -> c.getId() == id);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return users
                .stream()
                .filter(p -> p.getUsername().equals(username))
                .findFirst();
    }
}
