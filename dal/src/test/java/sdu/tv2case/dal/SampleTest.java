package sdu.tv2case.dal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SampleTest {

    int number = 1;
    Sample tester;

    @BeforeEach
    void setUp() {
        tester = new Sample(number);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void multiplyNumber() {
        assertEquals(number * 2, tester.multiplyNumber(2));
        assertNotEquals(number * 3, tester.multiplyNumber(2));

    }

}