package  com.example.cardgame;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.*;
public class TestCardclass {
private int Testvalue;
private Card TestCard;
@Before 
public void setUp(){
    
    Testvalue = 3;
}
@Test 
public void testGetValue(){
    Card TestCard = new Card(Testvalue);
    assertEquals(3, TestCard.getValue());
}
@Test 
public void testGetTurnsHeld(){
    Card TestCard = new Card(Testvalue);
    assertEquals(0,TestCard.getTurnsHeld());
}
@Test
public void testIncreaseTurnsHeld(){
    Card TestCard = new Card(Testvalue);
    TestCard.increaseTurnsHeld();
    assertEquals(1,TestCard.getTurnsHeld());
}
@Test
public void testResetTurnsHeld(){
    Card TestCard = new Card(Testvalue);
    TestCard.increaseTurnsHeld();

    TestCard.resetTurnsHeld();
    assertEquals(0, TestCard.getTurnsHeld());
}
}
