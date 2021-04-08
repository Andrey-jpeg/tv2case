package dal;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sdu.sem2.se17.dal.Sample;

import static org.junit.jupiter.api.Assertions.*;

public class SampleTest {
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
