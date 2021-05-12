package sdu.sem2.se17.domain.persistenceinterface;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import sdu.sem2.se17.domain.auth.User;

import static org.junit.jupiter.api.Assertions.*;

class UserHandlerTest {

    private UserHandler handler;

    @BeforeEach
    void setUp() {
        handler = new UserHandlerImplSample();
    }

    @DisplayName("CRUD operations")
    @Nested
    class Crud {
        @Test
        void create() {

        }
        @Test
        void read() {

        }
        @Test
        void update() {

        }
        @Test
        void delete() {

        }
    }
    @Test
    void findByUsername() {
    }
}