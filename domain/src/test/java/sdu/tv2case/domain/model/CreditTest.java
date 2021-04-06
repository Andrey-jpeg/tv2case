package sdu.tv2case.domain.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sdu.tv2case.dal.Sample;

import static org.junit.jupiter.api.Assertions.*;

class CreditTest {

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