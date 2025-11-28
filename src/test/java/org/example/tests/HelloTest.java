package org.example.tests;

import org.testng.Assert;
import org.testng.annotations.*;

public class HelloTest {

    @BeforeClass
    public void setupClass() {
        System.out.println("BeforeClass - setup test class");
    }

    @BeforeMethod
    public void setup() {
        System.out.println("BeforeMethod - setup before each test");
    }

    @Test(priority = 1)
    public void testAddition() {
        int a = 5, b = 3;
        Assert.assertEquals(a + b, 8, "Addition should be 8");
    }

    @Test(priority = 2)
    public void testSubtraction() {
        int a = 5, b = 3;
        Assert.assertEquals(a - b, 2, "Subtraction should be 2");
    }

    @AfterMethod
    public void tearDown() {
        System.out.println("AfterMethod - cleanup after each test");
    }

    @AfterClass
    public void tearDownClass() {
        System.out.println("AfterClass - finished test class");
    }
}
