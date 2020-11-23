package com.example.cookshop.items;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StepTest
{
    Step testStep1;
    Step testStep2;
    Step testStep3;
    Step testStep4;;
    Step testStep5;

    @Before
    public void setUp() throws Exception
    {
        testStep1 = new Step("Schritt 1", "Beschreibung Schritt eins", 42, 1);
        testStep2 = new Step("Schritt 2", "Beschreibung Schritt zwei", 12345, 2);
        testStep3 = new Step("Schritt 3", "Beschreibung Schritt drei", 0, 3);
        testStep4 = new Step("Schritt 4", "Beschreibung Schritt vier", 0, 4);
        testStep5 = new Step("Schritt 5", "Beschreibung Schritt fünf", 0, 5);
    }


    @Test
    public void getNameTest1()
    {
        assertTrue(testStep1.getName().equals("Schritt 1") &&
                testStep2.getName().equals("Schritt 2") &&
                testStep3.getName().equals("Schritt 3"));
    }

    @Test
    public void getDescriptionTest1()
    {
        assertTrue(testStep1.getDescription().equals("Beschreibung Schritt eins") &&
                testStep2.getDescription().equals("Beschreibung Schritt zwei") &&
                testStep3.getDescription().equals("Beschreibung Schritt drei"));
    }

    @Test
    public void getTimerTest1()
    {
        assertTrue(testStep1.getTimerInSeconds() == 42 &&
                testStep2.getTimerInSeconds() == 12345 &&
                testStep3.getTimerInSeconds() == 0 );
    }

    @Test
    public void getOrderPositionTest1()
    {
        assertTrue(testStep1.getOrderPosition() == 1    &&
                testStep2.getOrderPosition() == 2 &&
                testStep4.getOrderPosition() == 4 );
    }


    @Test
    public void setNameTest1()
    {
        assertTrue(testStep1.getName().equals("Schritt 1") &&
                testStep2.getName().equals("Schritt 2") &&
                testStep4.getName().equals("Schritt 4"));

        testStep1.setName("neuer name 1");
        testStep2.setName("neuer name 3");

        assertTrue(testStep1.getName().equals("neuer name 1") &&
                testStep2.getName().equals("neuer name 3") &&
                testStep4.getName().equals("Schritt 4"));
    }



    @Test
    public void setDescriptionTest1()
    {
        assertTrue(testStep1.getDescription().equals("Beschreibung Schritt eins") &&
                testStep2.getDescription().equals("Beschreibung Schritt zwei") &&
                testStep3.getDescription().equals("Beschreibung Schritt drei"));

        testStep1.setDescription("Test beschreibung");
        testStep2.setDescription("Banane");

        assertTrue(testStep1.getDescription().equals("Test beschreibung") &&
                testStep2.getDescription().equals("Banane") &&
                testStep3.getDescription().equals("Beschreibung Schritt drei"));
    }



    @Test
    public void setDescriptionTest2()
    {
        assertTrue(testStep1.getDescription().equals("Beschreibung Schritt eins"));

        testStep1.setDescription("    Test beschreibung        ");

        assertTrue(testStep1.getDescription().equals("Test beschreibung"));
    }


    @Test
    public void setTimerTest1()
    {
        assertTrue(testStep1.getTimerInSeconds() == 42);

        testStep1.setTimerInSeconds(23);

        assertTrue(testStep1.getTimerInSeconds() == 23);
    }


    @Test
    public void setTimerTest2()
    {
        assertTrue(testStep1.getTimerInSeconds() == 42);

        testStep1.setTimerInSeconds(Integer.MAX_VALUE);

        assertTrue(testStep1.getTimerInSeconds() == Integer.MAX_VALUE);
    }





    @Test
    public void setOrderPositionTest1()
    {
        assertTrue(testStep1.getOrderPosition() == 1);

        testStep1.setOrderPosition(23);

        assertTrue(testStep1.getOrderPosition() == 23);
    }


    @Test
    public void setOrderPositionTest2()
    {
        assertTrue(testStep1.getOrderPosition() == 1);

        testStep1.setOrderPosition(Integer.MAX_VALUE);

        assertTrue(testStep1.getOrderPosition() == Integer.MAX_VALUE);
    }

    @Test
    public void compareToTest1()
    {
        testStep1.setName("ABC");
        testStep2.setName("BAC");
        testStep3.setName("CAB");
        assertTrue(testStep1.compareTo(testStep2)  ==1 &&
                testStep1.compareTo(testStep3) ==1);
    }

    @Test
    public void compareToTest2()
    {
        testStep1.setName("CAB");
        testStep2.setName("BAC");
        testStep3.setName("ABC");
        assertTrue(testStep1.compareTo(testStep2)  == -1 &&
                testStep2.compareTo(testStep3) == -1);
    }


    @Test
    public void compareToTest3()
    {
        testStep1.setName("Schritt");
        testStep2.setName("Schritt");
        testStep3.setName("Schritt");
        assertTrue(testStep1.compareTo(testStep2)  == 0 &&
                testStep2.compareTo(testStep3) == 0);
    }


    @Test
    public void setTimerTest3()
    {
        assertTrue(testStep1.getTimerInSeconds() == 42);

        testStep1.setTimerInSeconds(Integer.MAX_VALUE +1);

        //TODO Fix that behavior
        assertTrue(testStep1.getTimerInSeconds() == Integer.MAX_VALUE);
    }


    @Test
    public void setTimerTest4()
    {
        assertTrue(testStep1.getTimerInSeconds() == 42);

        testStep1.setTimerInSeconds(-123);

        //TODO Fix that behavior
        assertTrue(testStep1.getTimerInSeconds() == 0);
    }

}