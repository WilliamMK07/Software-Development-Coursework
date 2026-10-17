package cardgame;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.File;
import org.junit.*;

public class TestCardGameClass {
    CardGame TestCardGame;
    CardGame IncorrectfileCardGame;
    int TestPlayerNum;
    @Before 
    public void TestnewCardGame(){
        File TestFile = new File("Test.txt");
        File IncorrectFile = new File("sfs");
        this.TestPlayerNum = 4;
        this.TestCardGame = new CardGame(TestPlayerNum,TestFile);
        this.IncorrectfileCardGame = new CardGame(TestPlayerNum,IncorrectFile);
    }
    @Test 
    public void TestReadCardFile(){
        assertTrue(this.TestCardGame.readcardpack());
    } 
    @Test 
    public void Incorrectfile(){
    assertFalse(this.IncorrectfileCardGame.readcardpack());
 
    assertFalse(this.IncorrectfileCardGame.readcardpack());
    }
    @Test 
    public void TestGetTotalPlayers(){
        assertTrue(this.TestCardGame.getTotalPlayersnum() == this.TestPlayerNum);
    }
    @Test
    public void Testdistrubute(){
        this.TestCardGame.distrubuteCards(this.TestCardGame);
        assertTrue(this.TestCardGame.Players[0].showCurrentPlayerHand().equals("Player 1 Current Hand:  1 8 4 4"));
    }
}