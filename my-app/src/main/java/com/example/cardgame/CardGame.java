package com.example.cardgame;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;



public class CardGame{
    private static int TotalPlayersnum; //File containing the total number of players.
    private File CardPackFile; //File containing the cards to be used in the game
    private Card [] AllCards; //Contains all the card values 
    public static Player [] Players; // array of all the player objects to be made into threads
    public static Deck [] Decks; //contains all the Decks to be used in the game
    

    

    public CardGame(int ChosenPlayerNum, File Cpackfile){
        TotalPlayersnum = ChosenPlayerNum; 
        this.CardPackFile = Cpackfile;
        this.AllCards = new Card[8*ChosenPlayerNum];
        Players = new Player[ChosenPlayerNum];
        Decks = new Deck[ChosenPlayerNum];
    }
    public static int getTotalPlayersnum(){
        return TotalPlayersnum;
    }
    
    public boolean readcardpack(){
        int [] CardValuearray = new int[8*TotalPlayersnum]; //makes a list to contain the int values of all the cards to be made
        for (int z = 0; z <CardValuearray.length; z++){
            CardValuearray[z] = -1; //populates the value 
        }
        try (Scanner myReader = new Scanner(this.CardPackFile)){
            while(myReader.hasNextLine()){ //reads through each line in the CardPackFile 
                boolean ItemAdded = false;
                String Value = myReader.nextLine(); //gets the value of the integer on the current line 
                int num = Integer.parseInt(Value);
                if (num < 0){ //checks non positive card values havent been added 
                    return(false);
                }
                for (int i=0; i<CardValuearray.length;i++){
                    if (CardValuearray[i] == -1){
                        CardValuearray[i] = num; // if there is not a card in the index of the array add it to the array
                        ItemAdded = true;

                    }
                //System.out.println(CardValuearray[i]);
                if (ItemAdded == true){ //if a card has been added to the array during this loop read the next line
                    break;
                }
                }
                
            }
        for (int x=0; x<CardValuearray.length;x++){
            if (CardValuearray[x] == -1){ // If there are not enough cards to fill the array that means its an incomplete card pack 
            // so should not continue and return false
                return false; // Checks if file is too small eg 8n-1
            }
            else{
                this.AllCards[x] = new Card(CardValuearray[x]);
            }
        }
    }
    catch(FileNotFoundException e){
        System.out.println("No file entered");
        return(false);
    }
    return(true);
    }
    public void distrubuteCards(CardGame A){
        
        for(int x=0;x<TotalPlayersnum;x++){ // make the amount of decks and player objects as what has been inputted
            Players[x] = new Player(x+1,A);
            Decks[x] = new Deck(x+1,this.TotalPlayersnum);
        } 
        int CurrentPlayer = 0;
        for(int i = 0; i<AllCards.length;i++){ //filling up players hands with four cards then filling the decks with the remaining cards. 
            if(i<this.TotalPlayersnum*4){
                Players[CurrentPlayer].addCardToHand(AllCards[i]);
            }
            else{
                Decks[CurrentPlayer].addCardToDeck(AllCards[i]);

            }
            if (CurrentPlayer ==((this.TotalPlayersnum)-1)){ // if we have got to the 4th player/deck loop 
                CurrentPlayer = 0;
            }
            else{
                CurrentPlayer +=1;
            }
        }
        for(int x=0;x<TotalPlayersnum;x++){
           System.out.println("Player " + x + ": " + Players[x].showCurrentPlayerHand() + Decks[x].printPlayerDeck()); 
          try{// for each diffrent player writes to a diffrent file with their player number in the name their intial hand at the top
            BufferedWriter writer = new BufferedWriter(new FileWriter(Players[x].Filename));
            writer.write("Intial Hand:  " + Players[x].showCurrentPlayerHand());
            writer.newLine();
            writer.close();
          }
          catch(IOException e){
            System.out.println("File writing error");

          }
        }

    }
    public synchronized void PlayersTurn(int PlayerNum, AtomicBoolean WinnerFound){
        if (WinnerFound.get() == true){ //If a winner has already been found 
        // and the atomic boolean has already been found stop the method before the current player does another turn.
            return;
        }
        //PlayerNum is the array location of the player
        int NextDeckListLocation = PlayerNum+1; 
        int CurrentPlayerListLocation = PlayerNum;
        int CurrentDeckListLocation = PlayerNum;
        if (PlayerNum+1 == CardGame.getTotalPlayersnum()){ //If the next deck to discard to is deck 1 the deck number needs to reset
             NextDeckListLocation = 0;
        }
        else{
             NextDeckListLocation = PlayerNum+1;
        }
        
       Card DiscardedCardFromHand = Players[CurrentPlayerListLocation].CardToBeRemoved(); // creates a variable to hold the card removed from the players deck.
       
            
        Decks[NextDeckListLocation].addCardToDeck(DiscardedCardFromHand); //takes the card removed from the players deck and adds it to the next players deck.
        String DiscardLine = ("Player " + (PlayerNum+1) + " Discards a "+ DiscardedCardFromHand.getValue()+ " to  Deck " + (NextDeckListLocation+1));
        System.out.println(DiscardLine); // String that explains the discard of the players move so that it can be written to the output file.
        Card DrawnCardFromPlayersDeck = Decks[CurrentDeckListLocation].removeCardFromDeck();
        Players[CurrentPlayerListLocation].addCardToHand(DrawnCardFromPlayersDeck);
        String  DrawLine = (" Player " + (PlayerNum+1) + " Draws a " + DrawnCardFromPlayersDeck.getValue() + " from Deck " + (CurrentDeckListLocation+1));
        System.out.println(DrawLine);
        try{ //writing the players discard and pick up move to their 
            // System.out.println("Writing");
            BufferedWriter writer = new BufferedWriter(new FileWriter(Players[CurrentPlayerListLocation].Filename, true));
        
            writer.write(DiscardLine);
            writer.newLine();
            writer.newLine();
            writer.write(DrawLine);
            writer.newLine();
            writer.newLine();
            writer.write(Players[CurrentPlayerListLocation].showCurrentPlayerHand());
            writer.newLine();
            writer.newLine();
            writer.close();
            // System.out.println("File closed");
        }
        catch(IOException a){
            System.out.println("IO Error");
        }
        // If another winner has been found after this player has made theyre turn exit the method
         if (WinnerFound.get() == true){
            return;
        }
        //System.out.println(NextDeckListLocation + " I've added a card to " + PlayerNum+" DECK");
        
        System.out.println("Hand: "+ CardGame.Players[CurrentPlayerListLocation].showCurrentPlayerHand() +"DEck: "+CardGame.Decks[CurrentDeckListLocation].printPlayerDeck() + CardGame.Decks[NextDeckListLocation].printPlayerDeck());
       CardGame.Players[CurrentPlayerListLocation].showCurrentPlayerHand();
       // if the player has won after doing there turn set the Atomic boolean to true
       if(Players[CurrentDeckListLocation].CheckWin() == true){
        Players[CurrentDeckListLocation].SetWinner();
       }

    }
    

public static void main(String[] args) {
    Scanner myObj = new Scanner(System.in);
    int realTPlayersnum = 0;
    File myObj1 = new File("");
    while (true){
        while (true){
        System.out.println("Please enter the number of players");
        try{ 
        String TPlayersnum = myObj.nextLine(); //takes the 
        realTPlayersnum = Integer.parseInt(TPlayersnum); //turns the player number input into a Integer
        }
        catch (NumberFormatException e){
            System.out.println("Incorrect type please enter a whole number");
        }
            if (realTPlayersnum >0){
                break;
            }
        }
        boolean repeat = false;
        System.out.println("Please enter location of pack to load");
        String Filelocation = myObj.nextLine(); //takes the input of the file location as a string.
        System.out.println(Filelocation);
        myObj1 = new File(Filelocation);
        int counter = 0; // 
        // try-with-resources: Scanner will be closed automatically
        try (Scanner myReader = new Scanner(myObj1)) {
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                System.out.println(data);
                int num = Integer.parseInt(data);
                if (num <0){
                    repeat = true;
                }
                counter +=1;
            }
        } 
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        //System.out.println(counter);
        if(counter == (realTPlayersnum*8))
            if (repeat == false){
                break;
            }
    }
    CardGame Current = new CardGame(realTPlayersnum,myObj1); 
    Current.readcardpack();
    Current.distrubuteCards(Current);
    Thread [] ThreadList = new Thread [realTPlayersnum];  
    for (int i = 0; i<realTPlayersnum;i++){
        if (Players[i].CheckWin() == true){
            Players[i].PlayerWon(i+1); //writes the winner to all the diffrent output files if there is a winner 
            // before a players has taken a turn.
        }  
        ThreadList[i] = new Thread(Players[i]); //make a player thread for the number of players 
        ThreadList[i].start(); //starting each of the threads.

    
    
    }
    for (int z = 0; z<realTPlayersnum;z++){
        try{
                    ThreadList[z].join();


        }
        catch( InterruptedException e){
            System.out.println("I failed ");
        }

    // }/workspaces/Software-Development-Coursework/cardgame/Test.txt
    }
    for( int x=0; x<realTPlayersnum;x++){
        try{
        // System.out.println(Players[x].showCurrentPlayerHand());
        BufferedWriter writer = new BufferedWriter(new FileWriter(Players[x].Filename, true));
        writer.newLine();
        writer.write("Final Hand: " + Players[x].showCurrentPlayerHand());
        writer.close();

        }
        catch(IOException e){

        }
    }
        try{
            for( int y=0; y<TotalPlayersnum;y++){
            BufferedWriter writer = new BufferedWriter(new FileWriter(Decks[y].DeckFileName));
            writer.newLine();
            writer.write(Decks[y].printPlayerDeck());
            writer.newLine();
            writer.close();
            }
        }
          catch(IOException f){

          }
        }



    }


    

