package com.gyugyu.tobyspringexample;

import learningtest.template.Calculator;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class CalcSumTest {

    Calculator calculator;
    String numFilepath;

    @Before
    public void setUp() {
        calculator = new Calculator();
        numFilepath = getClass().getResource("/numbers.txt").getPath();
    }


    @Test
    public void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSum(numFilepath), is(10));
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calcMultiply(numFilepath), is(24));
    }
}
