package eu.pontsystems.testing;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {
    Calculator calculator = new Calculator();

    @Test
    void add() {
        assertEquals(4, calculator.add(2,2), "ERROR: 2+2 != 4?");
    }

    @Test
    void subtract() {
        assertEquals(2, calculator.subtract(4,2), "ERROR: 4-2 != 2?");
    }

    @Test
    void multiple() {
        assertEquals(4, calculator.multiple(2,2), "ERROR: 2*2 != 4?");
    }

    @Test
    void divide() {
        assertEquals(2, calculator.divide(4,2), "ERROR: 4/2 != 2?");
    }

}