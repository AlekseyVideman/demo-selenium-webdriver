package com.github.alekseyvideman.seleniumdemo;

import com.github.alekseyvideman.seleniumdemo.math.FibbonacciCalculator;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MathTest {

    @Test
    @Disabled
    public void shouldCalcFibb() {
        var calculator = new FibbonacciCalculator();

        assertEquals(34, calculator.calc(9));
        assertEquals(55, calculator.calc(10));
        assertEquals(377, calculator.calc(14));
    }
}
