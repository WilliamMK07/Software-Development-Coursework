package com.example.cardgame;
import java.io.File;
public class Deck {
    private int Start;
    private int End;
    private Card [] DeckQueue;
    private int DeckNum; // actual number -- not list location
    private int DeckLength;
    private int Decksize = 0;
    public File Deckfile;
    public String DeckFileName;

    public Deck (int DeckNum, int TotalPlayers){
        this.DeckNum = DeckNum;
        this.DeckLength = (4*TotalPlayers); 
        this.DeckQueue = new Card [DeckLength];
        this.Start = 0; //points to the front card in the list 
        this.End = 0; // points to the last card/most recently added card
        this.DeckFileName = "Deck " + DeckNum + ".txt"; // used for the buffrered file writer 
        // for writing to the deck output files
        this.Deckfile = new File(this.DeckFileName); // creating a File with the correspondent deck number to its contents
        
        
    }

    public synchronized void addCardToDeck(Card NewCard){
       



        this.DeckQueue[End] = NewCard; //sets the inputted card to the end position of the deck
        this.Decksize += 1; // card has been added to the deck size increments 
        End = (End + 1) % DeckLength; // if it reaches the end of the deck the end pointer raps around to the front.
        notify(); //wakes up the player who is to draw from current deck 
        
    }
    public synchronized boolean CheckDeck(){
        if (this.Decksize == 0){ //if the deck is currently empty 
            try{
                
                wait(); //freeze the deck waiting on the player to draw a card out of it to avoid an error and making 
                
            }
            catch(InterruptedException e) {}
            if (CardGame.Players[DeckNum-1].IsWinnerFound() == true)// stops the Deck from adding a 
                return false;
        }
        return true; //
    
    }
    public synchronized Card removeCardFromDeck(){ //all methods synchronized to avoid race conditions when threads access the decks
        Card PassedCard = this.DeckQueue[Start]; // gets the card 
        this.DeckQueue[Start] = null;
        this.Decksize -= 1; // card removed 
        Start = (Start + 1) % DeckLength; // resets the pointer if the decks start point reaches the end of deck
        return PassedCard; //return
    }
    public synchronized String printPlayerDeck(){ //looping through the deck array and adding each value to a string to return
        String Message = "deck"+this.DeckNum+" contents:";
        for (int i = 0;i<this.DeckLength ;i++){
            if ( this.DeckQueue[i] != null){
            Message = Message + " " + this.DeckQueue[i].getValue();
            }
        }
    return Message;
    } 
    public synchronized Card [] returnDeck(){
        return this.DeckQueue;
    }
    public boolean isEmpty(){
        int Count = 0;
        for (int r = 0; r<CardGame.getTotalPlayersnum();r++){
            if(this.DeckQueue[r] != null){
                Count += 1;
            }
        }
        if (Count == 0){
            return true;
        }
        else{
            return false;
        }
    }
    public int getDecksize(){
        return this.Decksize;
    }
    
}