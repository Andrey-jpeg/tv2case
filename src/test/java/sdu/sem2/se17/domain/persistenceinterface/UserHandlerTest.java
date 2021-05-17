package sdu.sem2.se17.domain.persistenceinterface;

import org.junit.jupiter.api.*;
import sdu.sem2.se17.domain.auth.Admin;
import sdu.sem2.se17.domain.auth.Producer;
import sdu.sem2.se17.domain.auth.User;
import sdu.sem2.se17.persistence.data.UserHandlerImpl;
import sdu.sem2.se17.persistence.db.DataSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserHandlerTest {

    private UserHandler handler;
    private final boolean connectToDb = false;
    private DataSource dataSource;


    @BeforeEach
    void setUp() {
        if (connectToDb){
            dataSource = new DataSource("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
            handler = new UserHandlerImpl(dataSource);
        }
    }

    @AfterEach
    void tearDown() {
        if(connectToDb){
            handler
                    .findByUsername("SampleProducer1")
                    .ifPresent(user -> handler.delete(user.getId()));
            handler
                    .findByUsername("SampleAdmin1")
                    .ifPresent(user -> handler.delete(user.getId()));

        }
    }

    @DisplayName("CRUD operations")
    @Nested
    class Crud {
        @Test
        void create() {
            if (!connectToDb){
                return;
            }

            for(User user: getUsers()){
                var result = handler.create(user);

                assertTrue(result.isPresent());
            }

        }
        @Test
        void read() {
            if (!connectToDb){
                return;
            }

            for(User user: getUsers()){
                var savedUser = handler.create(user);
                var result = handler.read(savedUser.get().getId());

                assertTrue(result.isPresent());
            }

        }
        @Test
        void update() {
            if (!connectToDb){
                return;
            }

            for(User tempUser: getUsers()){
                var startUser = handler.create(tempUser);

                startUser.get().setEmail("Changed email " +startUser.get().getUsername());
                startUser.get().setPassword("Whatever" );

                handler.update(startUser.get());

                var result = handler.read(startUser.get().getId());

                assertEquals(
                        startUser.get().getEmail(),
                        result.get().getEmail()
                );
            }


        }
        @Test
        void delete() {
            if (!connectToDb){
                return;
            }


            for(User tempUser: getUsers()){
                var savedUser = handler.create(tempUser);

                handler.delete(savedUser.get().getId());

                var result = handler.read(savedUser.get().getId());

                assertFalse(result.isPresent());
            }


        }
    }
    @Test
    void findByUsername() {
        if (!connectToDb){
            return;
        }

        for(User user: getUsers()){
            var savedUser = handler.create(user);

            var result = handler.findByUsername(savedUser.get().getUsername());

            assertTrue(result.isPresent());
        }

    }


    private ArrayList<User> getUsers(){
        return new ArrayList<User>(){{
            add(new Producer("SampleProducer1", "Sample2", "SampleProducer3", 1));
            add(new Admin("SampleAdmin1", "Sample2", "SampleAdmin3")
            );
        }};
    }


}