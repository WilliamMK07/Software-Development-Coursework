package cardgame;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

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
        this.TestCardGame.readcardpack();
        this.TestCardGame.distrubuteCards(this.TestCardGame);
        String TestHandstring = "Player 1 Current Hand:  1 8 4 4";
        System.out.println(this.TestCardGame.Players[0].showCurrentPlayerHand());
        assertTrue(this.TestCardGame.Players[0].showCurrentPlayerHand().equals(TestHandstring));
        for(int t = 0; t<this.TestPlayerNum;t++){
            assertTrue(this.TestCardGame.Players[t] != null);
            assertTrue(this.TestCardGame.Players[t].getHand().length == 4);

            assertTrue(this.TestCardGame.Decks[t].getDecksize() == 4);// 4 cards in each deck at start of game
            //assertTrue(this.TestCardGame.Decks[t][0] != null); 
        }
        
    }

    @Test 
    public void TestPlayersTurn(){
        File TestFile = new File("Test.txt");
        CardGame NewGame = new CardGame(this.TestPlayerNum,TestFile);
        for (int i = 0 ; i<(NewGame.Players.length);i++){
            NewGame.Players[i] = new Player(i+1,NewGame);
            NewGame.Decks[i] = new Deck(i+1,this.TestPlayerNum);
            System.out.println(NewGame.Decks[i].printPlayerDeck());
        }
        assertTrue(NewGame.Players[0].isHandEmpty()); // checks hand is empty before adding cards
        NewGame.Players[0].addCardToHand(new Card(1));
        NewGame.Players[0].addCardToHand(new Card(2));
        NewGame.Players[0].addCardToHand(new Card(2));
        NewGame.Players[0].addCardToHand(new Card(3));
        assertTrue(NewGame.Decks[0].isEmpty()); // proves deck begins empty
        NewGame.Decks[0].addCardToDeck(new Card(4));
        assertTrue(NewGame.Decks[1].isEmpty()); // proves deck 2 is empty
        AtomicBoolean Winner = new AtomicBoolean(false);
        NewGame.PlayersTurn(0, Winner);
        assertFalse(NewGame.Decks[1].isEmpty()); // proves after players turn deck 2 has got a card in it now
        System.out.println(NewGame.Decks[1].printPlayerDeck());
        assertTrue(NewGame.Decks[1].printPlayerDeck().equals("deck2 contents: 2")); // proves 
        System.out.println(NewGame.Players[0].showCurrentPlayerHand());
        assertTrue(NewGame.Players[0].showCurrentPlayerHand().equals("Player 1 Current Hand:  1 4 2 3"));


        // Testing that it removes the card with the heighest turns held

        NewGame.Decks[0].addCardToDeck(new Card(1)); // add new card to deck
        NewGame.PlayersTurn(0,Winner);
        assertTrue(NewGame.Players[0].showCurrentPlayerHand().equals("Player 1 Current Hand:  1 4 1 3")); // this shows it chose to remove the 2 over 4 based off of turnsheld

    }
    @Test public void TestHandhasfour(){
        File TestFile = new File("Test.txt");
        CardGame NewGame2 = new CardGame(this.TestPlayerNum,TestFile);
        for (int i = 0 ; i<(NewGame2.Players.length);i++){
            NewGame2.Players[i] = new Player(i+1,NewGame2);
            NewGame2.Decks[i] = new Deck(i+1,this.TestPlayerNum);
            System.out.println(NewGame2.Decks[i].printPlayerDeck());
        
            for (int e =1;e<5;e++){
                NewGame2.Players[i].addCardToHand(new Card(e));
            }
            System.out.println(NewGame2.Players[i].showCurrentPlayerHand());
            NewGame2.Decks[0].addCardToDeck(new Card(1)); // add new card to deck
        }
        AtomicBoolean Winner = new AtomicBoolean(false);
        assertTrue(NewGame2.Players[0].showCurrentPlayerHand().equals("Player 1 Current Hand:  1 2 3 4"));
        assertTrue(NewGame2.Decks[0].printPlayerDeck().equals("deck1 contents: 1 1 1 1"));
        assertTrue(NewGame2.Decks[1].printPlayerDeck().equals("deck2 contents:"));
        NewGame2.PlayersTurn(0,Winner);
        assertTrue(NewGame2.Players[0].showCurrentPlayerHand().equals("Player 1 Current Hand:  1 1 3 4"));
        assertTrue(NewGame2.Decks[0].printPlayerDeck().equals("deck1 contents: 1 1 1"));
        assertTrue(NewGame2.Decks[1].printPlayerDeck().equals("deck2 contents: 2"));
        NewGame2.PlayersTurn(0,Winner);
        assertTrue(NewGame2.Players[0].showCurrentPlayerHand().equals("Player 1 Current Hand:  1 1 1 4"));
        assertTrue(NewGame2.Decks[0].printPlayerDeck().equals("deck1 contents: 1 1"));
        assertTrue(NewGame2.Decks[1].printPlayerDeck().equals("deck2 contents: 2 3"));
        NewGame2.PlayersTurn(1,Winner);
        assertTrue(NewGame2.Players[1].showCurrentPlayerHand().equals("Player 2 Current Hand:  2 2 3 4"));
        assertTrue(NewGame2.Decks[1].printPlayerDeck().equals("deck2 contents: 3"));
        assertTrue(NewGame2.Decks[2].printPlayerDeck().equals("deck3 contents: 1"));
        NewGame2.PlayersTurn(0,Winner);
        assertTrue(NewGame2.Players[0].CheckWin());
        assertTrue(NewGame2.Players[0].showCurrentPlayerHand().equals("Player 1 Current Hand:  1 1 1 1"));
    }
    
}