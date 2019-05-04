package com.gyugyu.tobyspringexample;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

public class JUnitTest {

    static Set<JUnitTest> testObjects = new HashSet<>();

    @Test
    public void test1(){
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);
    }

    @Test
    public void test2(){
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);
    }

    @Test
    public void test3(){
        assertThat(testObjects, not(hasItem(this)));
        testObjects.add(this);
    }

}
