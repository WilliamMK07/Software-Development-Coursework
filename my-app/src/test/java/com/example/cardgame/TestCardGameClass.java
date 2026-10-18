package com.example.cardgame;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.*;

public class TestCardGameClass {
    CardGame TestCardGame;
    CardGame IncorrectfileCardGame;
    CardGame NoWinCG;
    CardGame IncorrectNegativeNumberFileCG; 
    int TestPlayerNum;
    File TestFile;
    File TestFile3;
    File NoWinFile;
    File IncorrectNegativeNumberFile;
    @Before 
    public void TestnewCardGame() throws Exception{
        File TestFile = new File( getClass().getResource("/Test.txt").toURI());
        File TestFile3 = new File( getClass().getResource("/Test3.txt").toURI());
        File NoWinFile = new File( getClass().getResource("/NoWin.txt").toURI());
        File IncorrectNegativeNumberFile= new File( getClass().getResource("/IncorrectNegativeNumber.txt").toURI());


        File IncorrectFile = new File("sfs");
        this.TestPlayerNum = 4;
        this.TestCardGame = new CardGame(TestPlayerNum,TestFile);
        this.IncorrectfileCardGame = new CardGame(TestPlayerNum,IncorrectFile);
        this.IncorrectNegativeNumberFileCG = new CardGame(TestPlayerNum, IncorrectNegativeNumberFile);
        this.NoWinCG = new CardGame(TestPlayerNum,NoWinFile);
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
    public void TestFilewithNegativeCardNumbers(){
        assertFalse(this.IncorrectNegativeNumberFileCG.readcardpack());
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
    

    @Test
    public void TestThread(){
        int PlayerCount = 2;
        CardGame NewGame2 = new CardGame(PlayerCount,TestFile3);
        System.out.println((NewGame2.Players.length)+"WEEEEEEEEEEEEEEEEEEEEE");
        for (int i = 0 ; i<(NewGame2.Players.length);i++){
            NewGame2.Players[i] = new Player(i+1,NewGame2);
            NewGame2.Decks[i] = new Deck(i+1,this.TestPlayerNum);
            System.out.println(NewGame2.Decks[i].printPlayerDeck());
        
            for (int e =1;e<5;e++){
                NewGame2.Players[i].addCardToHand(new Card(e));
                NewGame2.Decks[i].addCardToDeck(new Card(5-e));
            }
            System.out.println(NewGame2.Players[i].showCurrentPlayerHand());
        }
        Thread [] ThreadList = new Thread [PlayerCount];  
        System.out.println(PlayerCount);
        for (int i = 0; i<PlayerCount;i++){
            if (NewGame2.Players[i].CheckWin() == true){
                NewGame2.Players[i].PlayerWon(i);
            }  
            ThreadList[i] = new Thread(NewGame2.Players[i]);
            ThreadList[i].start();

    
    
        }
        for (int i = 0; i<PlayerCount;i++){
            try{
            ThreadList[i].join();
            }
            catch(InterruptedException e){
                
            }
    
    
        }

        System.out.println(NewGame2.Players[0].IsWinnerFound()+"AAAAAAAAAAAAAAAAAAAA");
        assertTrue(NewGame2.Players[0].IsWinnerFound());

    }
    @Test public void conservationofCards(){
        int NoWinPlay = 4;
        for(int i =0; i<NoWinPlay;i++){
            this.NoWinCG.Players[i] = new Player(i+1,this.NoWinCG);
            this.NoWinCG.Decks[i] = new Deck(i+1,NoWinPlay);
        }
        this.NoWinCG.readcardpack();
        this.NoWinCG.distrubuteCards(this.NoWinCG);
        AtomicBoolean Winner = new AtomicBoolean(false);
        for (int turns = 0; turns <20; turns++){
            for(int i =0; i<NoWinPlay;i++){
                NoWinCG.PlayersTurn(i,Winner);
            }
        }
        int TotalCardCount = 0;
        for(int p=0; p<NoWinPlay;p++){
            Card [] Hand = NoWinCG.Players[p].getHand();
            for(int i =0; i<Hand.length;i++){
                if (Hand[i]!= null){
                    TotalCardCount +=1;
                }
            }
            TotalCardCount += NoWinCG.Decks[p].getDecksize();
            System.out.println("Hand: "+ NoWinCG.Players[p].showCurrentPlayerHand() +"DEck: "+NoWinCG.Decks[p].printPlayerDeck()+"QQQQQQQQQQQQQQQQQQQQQQQQQQ");
        }
        assertEquals(TotalCardCount, (8*NoWinPlay));
    
    }
    @Test 
    public void Earlywin(){
        int TestPlayerNum2 = 2;
        CardGame NewGame3 = new CardGame(TestPlayerNum2,TestFile);
        for (int i = 0 ; i<(NewGame3.Players.length);i++){
            NewGame3.Players[i] = new Player(i+1,NewGame3);
            NewGame3.Decks[i] = new Deck(i+1,TestPlayerNum2);
        }
        for (int e =1;e<5;e++){
            NewGame3.Players[0].addCardToHand(new Card(5));
            NewGame3.Players[1].addCardToHand(new Card(e));
            NewGame3.Decks[0].addCardToDeck(new Card(e));
            NewGame3.Decks[1].addCardToDeck(new Card(e));
        }
        Thread [] ThreadList = new Thread [TestPlayerNum2];  
        
        for (int y= 0; y<TestPlayerNum2;y++){
            if (NewGame3.Players[y].CheckWin() == true){
                NewGame3.Players[y].PlayerWon(y);
            }  
            ThreadList[y] = new Thread(NewGame3.Players[y]);
            ThreadList[y].start();

    
    
        }
        for (int x = 0; x<TestPlayerNum2;x++){
            try{
            ThreadList[x].join();
            }
            catch(InterruptedException e){
                
            }
        String winningHandOfNonPrefaredNumber = "Player 1 Current Hand:  5 5 5 5";
        String losingUnchangedHandOfTheOther ="Player 2 Current Hand:  1 2 3 4";
        System.out.println(NewGame3.Players[0].showCurrentPlayerHand());
        System.out.println(NewGame3.Players[1].showCurrentPlayerHand());

        assertEquals(winningHandOfNonPrefaredNumber,NewGame3.Players[0].showCurrentPlayerHand());
        assertTrue(NewGame3.Players[0].IsWinnerFound() == true && NewGame3.Players[1].IsWinnerFound() == true);
        }
    }
    
    
    
        
        



    }


