package sdu.sem2.se17.domain;

import org.junit.jupiter.api.*;
import sdu.sem2.se17.domain.auth.Admin;
import sdu.sem2.se17.domain.auth.Producer;
import sdu.sem2.se17.domain.auth.User;
import sdu.sem2.se17.domain.credit.Role;
import sdu.sem2.se17.domain.production.Approval;
import sdu.sem2.se17.domain.production.Production;
import sdu.sem2.se17.domain.production.ProductionCompany;

import java.util.ArrayList;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class CreditManagementControllerTest {

    private CreditManagementController controller;

    private final ArrayList<User> users = new ArrayList<>() {{
        add(new Admin("SysAdmin", "password", "john@sdu.student.dk"));
        add(new Admin("Lorem", "", "test@sdu.student.dk"));


        add(new Producer("John", "password", "john@sdu.student.dk", 0));
        add(new Producer("tester 123", "", "test@sdu.student.dk", 1));

        add(new Admin("Ipsum", "aaaa2231", "1133@sdu.student.dk"));
        add(new Admin("Dolem", "551231", "55123@sdu.student.dk"));

        add(new Producer("AnotherUser11", "tihi123", "aa@sdu.student.dk", 2));
        add(new Producer("Producer4", "uhyes", "ddd@sdu.student.dk", 3));

    }};
    private final ArrayList<ProductionCompany> companies = new ArrayList<>() { {

    } };
    private final ArrayList<Production> productions = new ArrayList<>() { {
        add(new Production(0, "A production from DR"));
        add(new Production(0, "Another production from DR"));
        add(new Production(0, "Another prosasdasdduction from DR"));
        add(new Production(0, "Another producasdasdtion from DR"));
        add(new Production(2, "A movie"));
        add(new Production(3, "Something something"));
    } };

    @BeforeEach
    void setUp() {
        controller = new CreditManagementControllerImplDomain(users, productions, companies);
    }

    @AfterEach
    void tearDown() {
    }

    @DisplayName("login() method")
    @Nested
    class LoginMethod {
        @Test
        void loginSuccessful() {
            for (User user : users) {
                assertTrue(controller.login(user.getUsername(), user.getPassword()));
            }
        }
        @Test
        void sessionUserCorrect() {
            for (User user : users) {
                controller.login(user.getUsername(), user.getPassword());
                assertEquals(user instanceof Admin, controller.isAdmin());
            }
        }
        @Test
        void failsIfIncorrect() {
            for (User user : users) {
                assertFalse(controller.login(user.getUsername() + "incorrect username", user.getPassword()));
                assertFalse(controller.login(user.getUsername(), user.getPassword() + "incorrect password"));
            }
        }
    }



    @DisplayName("findProduction methods")
    @Nested
    class FindProduction {

        @BeforeEach
        void login(){
            var user = users.get(2);
            assertTrue(user instanceof Producer);
            controller.login(user.getUsername(), user.getPassword());
        }

        @Test
        void findByName() {
            var expectedProduction = productions.get(2);
            assertEquals(expectedProduction, controller.findProduction(expectedProduction.getName()));
        }

        @Test
        void findByIndex() {
            var expectedProduction = controller.getProductions().get(1);
            assertEquals(expectedProduction, controller.findProduction(1));
        }
    }

    @DisplayName("Manipulate production credits")
    @Nested
    class ProductionCredits {
        @Test
        void addCredit() {
            var user = users.get(2);
            assertTrue(user instanceof Producer);
            controller.login(user.getUsername(), user.getPassword());

            var expectedProduction = controller.getProductions().get(1);
            var name = "John";
            var role = Role.ANIMATION.toString();

            controller.addCreditToProduction(1, name, role);

            assertAll("Credit should be added correctly",
                    () -> assertEquals(name, expectedProduction.getCredits().get(0).getParticipant().getName()),
                    () -> assertEquals(Role.ANIMATION, expectedProduction.getCredits().get(0).getRole())
            );
        }

        @Test
        void deleteCredit() {
            var user = users.get(2);
            assertTrue(user instanceof Producer);
            controller.login(user.getUsername(), user.getPassword());


            var expectedProduction = controller.getProductions().get(1);
            var name = "John";
            var role = Role.ANIMATION.toString();

            controller.addCreditToProduction(1, name, role);
            var oldSize = expectedProduction.getCredits().size();

            controller.deleteCredit(1, 0);

            assertEquals(oldSize - 1, expectedProduction.getCredits().size());

        }
    }


    @Test
    void createProduction() {
        var oldLength = productions.size();
        var name = "NewProduction11";
        long companyId = 1;

        controller.createProduction(companyId, name);
        assertEquals(oldLength + 1, productions.size());
        assertEquals(name, productions.get(productions.size() -1).getName());
    }

    @Test
    void createProducer() {
        var oldLength = users.size();
        var username = "NewProducer";
        var password = "NewProducer";
        var email = "NewProducer";
        long companyId = 1;

        controller.createProducer(username, password, email, companyId);
        assertEquals(oldLength + 1, users.size());
        assertEquals(username, users.get(users.size() -1).getUsername());
    }


    @DisplayName("Get productions for user")
    @Nested
    class GetProductions{
        @Test
        void notAuthenticated() {
            var aa = controller.getProductions();
            assertNull(controller.getProductions());
        }

        @Test
        void getForProducer() {
            var user = users.get(2);
            assertTrue(user instanceof Producer);
            controller.login(user.getUsername(), user.getPassword());

            var userProductions = productions
                    .stream()
                    .filter(c -> c.getCompanyId() == ((Producer) user).getCompanyId())
                    .collect(Collectors.toList());
            assertTrue(controller.getProductions().containsAll(userProductions));
        }

        @Test
        void getForAdmin() {
            var user = users.get(1);
            controller.login(user.getUsername(), user.getPassword());

            assertEquals(productions, controller.getProductions());
        }

    }


    @Test
    void getCompanies() {
        assertEquals(companies, controller.getCompanies());
    }


    @Test
    void validateProduction() {
        var name = "validateProductionTest";

        var user = users.get(2);
        assertTrue(user instanceof Producer);
        controller.login(user.getUsername(), user.getPassword());

        controller.createProduction(((Producer) user).getCompanyId(), name);
        var production = controller.findProduction(name);
        var id = controller.getProductions().indexOf(production);

        assertFalse(production.getApproval().isApproved());

        controller.validateProduction(id, Approval.APPROVED);

        assertTrue(production.getApproval().isApproved());

    }




}