package  com.example.cardgame;


import static org.junit.Assert.assertEquals;
import org.junit.*;
public class TestCardclass {
private int Testvalue;
@Before 
public void setUp(){
    
    Testvalue = 3; //setting the value of the first card to be tested
}
@Test 
public void testGetValue(){
    Card TestCard = new Card(Testvalue); //instantiating a card with the test value
    assertEquals(3, TestCard.getValue()); // testing the get method returns the correct integer value of the card.
}
@Test 
public void testGetTurnsHeld(){
    Card TestCard = new Card(Testvalue); 
    assertEquals(0,TestCard.getTurnsHeld()); //testing that when a card is instatiated the get method 
    // returns the default value of turns held 
}
@Test
public void testIncreaseTurnsHeld(){ // calling increase turns held and checking iff its get method returns 1
    Card TestCard = new Card(Testvalue);
    TestCard.increaseTurnsHeld();
    assertEquals(1,TestCard.getTurnsHeld());
}
@Test
public void testResetTurnsHeld(){ // resets the turns after increasing them
    Card TestCard = new Card(Testvalue);
    TestCard.increaseTurnsHeld();
    assertEquals(1, TestCard.getTurnsHeld());
    TestCard.resetTurnsHeld();
    assertEquals(0, TestCard.getTurnsHeld());
}
}
