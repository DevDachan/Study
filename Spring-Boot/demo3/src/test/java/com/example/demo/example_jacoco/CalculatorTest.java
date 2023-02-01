package com.example.demo.example_jacoco;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.hamcrest.core.Is.is;
import static org.assertj.core.api.Assertions.assertThat;

public class CalculatorTest {
    private Calculator calculator = new Calculator();

    @Test
    public void addTest(){
        int actual = calculator.add(1,2);
        int expected = 3;

        Assertions.assertEquals(actual, expected);
    }

    @Test
    public void multipleTest(){
        int actual = calculator.multiple(1,2);
        int expected = 2;

        Assertions.assertEquals(actual, expected);
    }
}
