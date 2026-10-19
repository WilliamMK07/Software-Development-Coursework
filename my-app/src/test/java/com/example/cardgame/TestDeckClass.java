package com.example.cardgame;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;

public class TestDeckClass {
    private int testDeckNum =1;
    private int testTotalPlayers=3;

   @Test
   public void Testaddnewcard(){
    Deck TestDeck = new Deck(testDeckNum,testTotalPlayers); //instantiating a deck to test
    Card TestCard1 = new Card(5); // instantiating a card 
    TestDeck.addCardToDeck(TestCard1); // adding the card to a deck to test whether a card is added when inputted is 
    String Teststring = "deck1 contents: 5"; // string that should match the contents of the deck after adding a card with value 5 to it
    assertTrue(Teststring.equals(TestDeck.printPlayerDeck())); // testing that the expected output is equal to what the deck outputs
    }
   @Test
   public void TestRemoveCardFromDeck(){
    Deck TestDeck = new Deck(testDeckNum,testTotalPlayers); 
    Card TestCard1 = new Card(5);
    Card TestCard2 = new Card(12);
    TestDeck.addCardToDeck(TestCard1); // adding 5 then 12 
    TestDeck.addCardToDeck(TestCard2); 
    String TestShouldbestring =  "deck1 contents: 12";
    Card testRemovedCard = TestDeck.removeCardFromDeck(); // removing 5 from the top of the deck 
    Card TestCard3 = new Card(7);
    assertEquals(5,testRemovedCard.getValue()); // tests that the card removed is the top card that should be equal to 5
    assertTrue(TestShouldbestring.equals(TestDeck.printPlayerDeck())); //tests that the decks contents after removing the 5
    TestDeck.addCardToDeck(TestCard3); // adds another card to be sure that FIFO properties have been correctly tested/displayed
    String TestShouldbeOutputFIFO = "deck1 contents: 12 7";
    assertTrue(TestShouldbeOutputFIFO.equals(TestDeck.printPlayerDeck())); //Checks the 7 card is at the back of the deck



   }
   @Test 
   public void testCheckDeckOutsideofgameloop(){
    Deck TestDeck = new Deck(testDeckNum,testTotalPlayers); 
    Card TestCard1 = new Card(9);
    TestDeck.addCardToDeck(TestCard1);
    System.out.println(TestDeck.CheckDeck());

    assertTrue(TestDeck.CheckDeck()); // tests checkdeck works outside of the game loop allowing a non empty deck to continue.
    
   } 
}
