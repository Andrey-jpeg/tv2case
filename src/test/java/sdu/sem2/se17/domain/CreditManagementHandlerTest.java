package sdu.sem2.se17.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import sdu.sem2.se17.domain.auth.Admin;
import sdu.sem2.se17.domain.auth.User;
import sdu.sem2.se17.domain.persistenceinterface.ParticipantHandlerImplSample;
import sdu.sem2.se17.persistence.data.ParticipantHandlerImpl;
import sdu.sem2.se17.persistence.db.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class CreditManagementHandlerTest {

    private CreditManagementHandler handler;
    private final boolean connectToDb = false;

    private final static String defAdminUsername = "sysadmin";
    private final static String defAdminPassword = "1234";

    private final static String defProducerUsername = "DR1";
    private final static String defProducerPassword = "2345";

    @BeforeEach
    void setUp() {
        if (connectToDb){
            DataSource dataSource = new DataSource("jdbc:postgresql://localhost:5432/", "postgres", "postgres");
            handler = new CreditManagementHandlerImpl(dataSource);
        }
    }

    @DisplayName("login() method")
    @Nested
    class LoginMethod {
        @Test
        void loginSuccessful() {
            if (!connectToDb){
                return;
            }

            assertTrue(handler.login(defAdminUsername, defAdminPassword));
            assertTrue(handler.login(defProducerUsername, defProducerPassword));
        }
        @Test
        void sessionUserCorrect() {
            if (!connectToDb){
                return;
            }

            handler.login(defAdminUsername, defAdminPassword);
            assertTrue(handler.isAdmin());
            handler.login(defProducerUsername, defProducerPassword);
            assertFalse(handler.isAdmin());
        }
        @Test
        void failsIfIncorrect() {
            if (!connectToDb){
                return;
            }

            assertFalse(handler.login(defAdminUsername+ "incorrect username", defAdminPassword));
            assertFalse(handler.login(defAdminUsername, defAdminPassword + "incorrect password"));
        }
    }

    @Test
    void deleteUser() {
    }

    @Test
    void createUser() {
    }

    @Test
    void testCreateUser() {
    }

    @Test
    void getProductions() {
    }

    @Test
    void findProduction() {
    }

    @Test
    void testFindProduction() {
    }

    @Test
    void createProduction() {
    }

    @Test
    void updateProduction() {
    }

    @Test
    void deleteProduction() {
    }

    @Test
    void createParticipant() {
    }

    @Test
    void findParticipant() {
    }

    @Test
    void testFindParticipant() {
    }

    @Test
    void deleteParticipant() {
    }

    @Test
    void getCompanies() {
    }

    @Test
    void createCompany() {
    }
}