package sdu.sem2.se17.domain;

import org.apache.commons.httpclient.methods.multipart.Part;
import org.junit.jupiter.api.*;
import sdu.sem2.se17.domain.auth.Admin;
import sdu.sem2.se17.domain.auth.Producer;
import sdu.sem2.se17.domain.auth.User;
import sdu.sem2.se17.domain.credit.Participant;
import sdu.sem2.se17.domain.persistenceinterface.*;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.persistence.data.ParticipantHandlerImpl;
import sdu.sem2.se17.persistence.data.ProductionCompanyHandlerImpl;
import sdu.sem2.se17.persistence.data.ProductionHandlerImpl;
import sdu.sem2.se17.persistence.data.UserHandlerImpl;
import sdu.sem2.se17.persistence.db.DataSource;

import static org.junit.jupiter.api.Assertions.*;

class CreditManagementHandlerTest {

    private UserHandler userHandler;
    private CreditManagementHandler handler;
    private ParticipantHandler participantHandler;
    private ProductionCompanyHandler companyHandler;
    private ProductionHandler productionHandler;
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
            userHandler = new UserHandlerImpl(dataSource);
            participantHandler = new ParticipantHandlerImpl(dataSource);
            companyHandler = new ProductionCompanyHandlerImpl(dataSource);
            productionHandler = new ProductionHandlerImpl(dataSource);
        }
    }

    @AfterEach
    void tearDown() {
        if(connectToDb){
            userHandler
                    .findByUsername("XD")
                    .ifPresent(user -> userHandler.delete(user.getId()));
            companyHandler
                    .readAll()
                    .stream()
                    .filter(p -> p.getName().equals("Company1") ||p.getName().equals("Company2"))
                    .forEach(p -> companyHandler.delete(p.getId()));
            productionHandler
                    .readAll()
                    .stream()
                    .filter(p -> p.getName().equals("bruh") ||
                            p.getName().equals("Bruh2") ||
                            p.getName().equals("xDBruh") ||
                            p.getName().equals("TestProduction") ||
                            p.getName().equals("Changed name"))
                    .forEach(p -> productionHandler.delete(p.getId()));
            participantHandler
                    .findByName("bruh")
                    .forEach(p -> participantHandler.delete(p.getId()));
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
        if (!connectToDb){
            return;
        }
        long userId = userHandler.create(new Producer("XD", "XD2", "xD@xD.com", 1)).get().getId();
        handler.deleteUser(userId);
        assertFalse(userHandler.read(userId).isPresent());
    }

    @Test
    void createUser() {
        if (!connectToDb){
            return;
        }
        handler.createUser("XD", "XD2", "xD@xD.com", 1);
        assertTrue(userHandler.findByUsername("XD").isPresent());
    }

    @Test
    void testCreateUser() {
        if (!connectToDb){
            return;
        }
        handler.createUser("XD", "XD2", "xD@xD.com");
        assertTrue(userHandler.findByUsername("XD").isPresent());
    }

    @Test
    void getProductions() {
        if (!connectToDb){
            return;
        }
        handler.login(defProducerUsername, defProducerPassword);
        int initLength = handler.getProductions().size();
        handler.createProduction("bruh");
        handler.createProduction("bruh2");
        handler.createProduction("xDBruh");
        int finalLength = handler.getProductions().size();
        assertEquals(3, finalLength - initLength);
    }

    @Test
    void findProduction() {
        if (!connectToDb){
            return;
        }
        handler.login(defProducerUsername, defProducerPassword);
        Production production = handler.createProduction("TestProduction");
        assertNotNull(handler.findProduction(production.getId()));
    }


    @Test
    void createProduction() {
        if (!connectToDb){
            return;
        }
        handler.login(defProducerUsername, defProducerPassword);
        Production production = handler.createProduction("TestProduction");
        assertNotNull(production);
    }

    @Test
    void updateProduction() {
        if (!connectToDb) {
            return;
        }
        handler.login(defProducerUsername, defProducerPassword);
        Production startProduction = handler.createProduction("TestProduction");
        startProduction.setName("Changed name");
        startProduction.setComments("new comments");
        handler.updateProduction(startProduction);
        var result = handler.getProductions().stream()
                .filter(p -> p.getId() == startProduction.getId())
                .findFirst();
        assertAll(
                () -> assertEquals(
                        startProduction.getId(),
                        result.get().getId()),
                () -> assertEquals(
                        startProduction.getCompanyId(),
                        result.get().getCompanyId()),
                () -> assertEquals(
                        startProduction.getApproval(),
                        result.get().getApproval()),
                () -> assertEquals(
                        startProduction.getName(),
                        result.get().getName()),
                () -> assertEquals(
                        startProduction.getComments(),
                        result.get().getComments())
        );
    }

    @Test
    void deleteProduction() {
        if (!connectToDb){
            return;
        }
        handler.login(defProducerUsername, defProducerPassword);
        Production production = handler.createProduction("TestProduction");
        long productionId = production.getId();
        handler.deleteProduction(productionId);
        assertFalse(handler.getProductions().stream()
        .filter(p -> p.getId() == productionId)
        .findFirst().isPresent());
    }

    @Test
    void createParticipant() {
        if (!connectToDb){
            return;
        }
        int initValue = handler.findParticipant("bruh").size();
        handler.createParticipant("bruh");
        int finalValue = handler.findParticipant("bruh").size();
        assertEquals(1, finalValue - initValue);
    }

    @Test
    void findParticipant() {
        if (!connectToDb){
            return;
        }
        Participant participant = participantHandler.create(new Participant("bruh")).get();
        assertNotNull(handler.findParticipant(participant.getId()));
    }

    @Test
    void testFindParticipant() {
        if (!connectToDb){
            return;
        }
        Participant participant = participantHandler.create(new Participant("bruh")).get();
        assertEquals(1, handler.findParticipant(participant.getName()).size());
    }

    @Test
    void deleteParticipant() {
        if (!connectToDb){
            return;
        }
        Participant participant = participantHandler.create(new Participant("bruh")).get();
        long participantId = participant.getId();
        handler.deleteParticipant(participantId);
        assertNull(handler.findParticipant(participantId));
    }

    @Test
    void getCompanies() {
        if (!connectToDb){
            return;
        }
        int companiesSize = handler.getCompanies().size();
        handler.createCompany("Company1");
        handler.createCompany("Company2");
        int finalCompaniesSize = handler.getCompanies().size();
        assertEquals(2, finalCompaniesSize - companiesSize);
    }

    @Test
    void createCompany() {
        if (!connectToDb){
            return;
        }
        handler.createCompany("Company1");
        assertNotNull(handler.getCompanies().stream()
        .filter(p -> p.getName().equals("Company1"))
        .findFirst());
    }
}