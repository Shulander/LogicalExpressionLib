/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicalexpression.model;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Shulander
 */
public class EOperatorTest {
    
    public EOperatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of compare method, of class EOperator.
     */
    @Test
    public void testCompare() {
        System.out.println("compare");
        Comparable a = 5;
        Comparable b = 10;
        EOperator instance = EOperator.EQ;
        Boolean result = instance.compare(a, b);
        assertFalse(result);
        instance = EOperator.GE;
        result = instance.compare(a, b);
        assertFalse(result);
        instance = EOperator.LE;
        result = instance.compare(a, b);
        assertTrue(result);
        instance = EOperator.LT;
        result = instance.compare(a, b);
        assertTrue(result);
        instance = EOperator.NE;
        result = instance.compare(a, b);
        assertTrue(result);
    }

    /**
     * Test of parseString method, of class EOperator.
     */
    @Test
    public void testParseString() {
        System.out.println("parseString");
        String str = "==";
        EOperator expResult = EOperator.EQ;
        EOperator result = EOperator.parseString(str);
        assertEquals(expResult, result);
    }

    
}
