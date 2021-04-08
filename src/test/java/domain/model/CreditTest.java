package domain.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sdu.sem2.SE17.dal.Sample;
import sdu.sem2.SE17.domain.model.Credit;

import static org.junit.jupiter.api.Assertions.*;

public class CreditTest {
    int number = 2;
    Credit tester;

    @BeforeEach
    void setUp() {
        tester = new Credit(number);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void multiplyNumber() {
        assertEquals(number * 2, tester.multiplyNumber(2));
        assertNotEquals(number * 3, tester.multiplyNumber(2));
    }

    @Test
    void sample() {
        assertTrue(tester.sample() instanceof Sample);
    }
}
