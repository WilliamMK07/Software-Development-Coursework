package cardgame;
import org.junit.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class TestDeckClass {
    private int testDeckNum =1;
    private int testTotalPlayers=3;

    @Before 
    public void setUp(){
     Deck TestDeck = new Deck(testDeckNum,testTotalPlayers);
    }
    @After 
    public void cleanUp(){
        Deck TestDeck = null;
    }
   @Test
   public void Testaddnewcard(){
    Deck TestDeck = new Deck(testDeckNum,testTotalPlayers);
    Card TestCard1 = new Card(5);
    TestDeck.addCardToDeck(TestCard1);
    String Teststring = "deck1 contents: 5";
    System.out.println(TestDeck.printPlayerDeck());
    System.out.println( Teststring);
    assertTrue(Teststring.equals(TestDeck.printPlayerDeck()));
    }
   @Test
   public void TestRemoveCardFromDeck(){
    Deck TestDeck = new Deck(testDeckNum,testTotalPlayers);
    Card TestCard1 = new Card(5);
    Card TestCard2 = new Card(12);
    TestDeck.addCardToDeck(TestCard1);
    TestDeck.addCardToDeck(TestCard2);
    String TestShouldbestring =  "deck1 contents: 12";
    Card testRemovedCard = TestDeck.removeCardFromDeck();
    assertEquals(5,testRemovedCard.getValue());
    assertTrue(TestShouldbestring.equals(TestDeck.printPlayerDeck()));
    //assertFalse(Tes)


   }
   @Test 
   public void testCheckDeck(){
    Deck TestDeck = new Deck(testDeckNum,testTotalPlayers);
    
   }
}
