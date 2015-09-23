/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logicalexpression.model;

import logicalexpression.model.operand.Operand;
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
        Operand a = Operand.build(5);
        Operand b = Operand.build(10);
        EOperator instance = EOperator.EQ;
        Operand result = instance.compare(a, b);
        assertEquals(result, Operand.build(false));
        instance = EOperator.GE;
        result = instance.compare(a, b);
        assertEquals(result, Operand.build(false));
        instance = EOperator.LE;
        result = instance.compare(a, b);
        assertEquals(result, Operand.build(true));
        instance = EOperator.LT;
        result = instance.compare(a, b);
        assertEquals(result, Operand.build(true));
        instance = EOperator.NE;
        result = instance.compare(a, b);
        assertEquals(result, Operand.build(true));
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
