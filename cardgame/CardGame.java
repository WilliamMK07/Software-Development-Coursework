package cardgame;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

import java.io.Writer;


public class CardGame{
    private static int TotalPlayersnum;
    private File CardPackFile;
    private Card [] AllCards;
    public static Player [] Players;
    public static Deck [] Decks;
    

    

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
        int [] CardValuearray = new int[8*TotalPlayersnum];
        for (int z = 0; z <CardValuearray.length; z++){
            CardValuearray[z] = -1;
        }
        try (Scanner myReader = new Scanner(this.CardPackFile)){
            while(myReader.hasNextLine()){
                boolean ItemAdded = false;
                String Value = myReader.nextLine();
                int num = Integer.parseInt(Value);
                for (int i=0; i<CardValuearray.length;i++){
                    if (CardValuearray[i] == -1){
                        //System.out.println(CardValuearray[i] +" "+  i + "     "+ num);
                        CardValuearray[i] = num;
                        ItemAdded = true;

                    }
                //System.out.println(CardValuearray[i]);
                if (ItemAdded == true){
                    break;
                }
                }
                
            }
        for (int x=0; x<CardValuearray.length;x++){
            if (CardValuearray[x] == -1){
                return false; // Checks if file is too small eg 8n-1
            }
            else{
                //System.out.println(this.AllCards[x]+"WEEEEEEEEEEEEEEEEEEEEE");
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
        
        for(int x=0;x<TotalPlayersnum;x++){
            Players[x] = new Player(x+1,A);
            Decks[x] = new Deck(x+1,this.TotalPlayersnum);
        }
        int CurrentPlayer = 0;
        for(int i = 0; i<AllCards.length;i++){
            if(i<this.TotalPlayersnum*4){
                Players[CurrentPlayer].addCardToHand(AllCards[i]);
            }
            else{
                Decks[CurrentPlayer].addCardToDeck(AllCards[i]);

            }
            if (CurrentPlayer ==((this.TotalPlayersnum)-1)){
                CurrentPlayer = 0;
            }
            else{
                CurrentPlayer +=1;
            }
        }
        for(int x=0;x<TotalPlayersnum;x++){
           System.out.println("Player " + x + ": " + Players[x].showCurrentPlayerHand() + Decks[x].printPlayerDeck()); 
          try{
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
        if (WinnerFound.get() == true){
            return;
        }
        //PlayerNum is the array location of the player
        int NextDeckListLocation = PlayerNum+1;
        int CurrentPlayerListLocation = PlayerNum;
        int CurrentDeckListLocation = PlayerNum;
        if (PlayerNum+1 == CardGame.getTotalPlayersnum()){
             NextDeckListLocation = 0;
        }
        else{
             NextDeckListLocation = PlayerNum+1;
        }
        
       Card DiscardedCardFromHand = Players[CurrentPlayerListLocation].CardToBeRemoved();
       
            
        Decks[NextDeckListLocation].addCardToDeck(DiscardedCardFromHand);
        String DiscardLine = ("Player " + (PlayerNum+1) + " Discards a "+ DiscardedCardFromHand.getValue()+ " to  Deck " + (NextDeckListLocation+1));
        System.out.println(DiscardLine);
        // Add FIle writing for discard here
        //System.out.println(NextDeckListLocation + " I've removed a card from "+ PlayerNum + " HAND");
        Card DrawnCardFromPlayersDeck = Decks[CurrentDeckListLocation].removeCardFromDeck();
        Players[CurrentPlayerListLocation].addCardToHand(DrawnCardFromPlayersDeck);
        String  DrawLine = (" Player " + (PlayerNum+1) + " Draws a " + DrawnCardFromPlayersDeck.getValue() + " from Deck " + (CurrentDeckListLocation+1));
        System.out.println(DrawLine);
        try{
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
        // Add file writing for adding to hand here
         if (WinnerFound.get() == true){
            return;
        }
        //System.out.println(NextDeckListLocation + " I've added a card to " + PlayerNum+" DECK");
        
        System.out.println("Hand: "+ CardGame.Players[CurrentPlayerListLocation].showCurrentPlayerHand() +"DEck: "+CardGame.Decks[CurrentDeckListLocation].printPlayerDeck() + CardGame.Decks[NextDeckListLocation].printPlayerDeck());
       CardGame.Players[CurrentPlayerListLocation].showCurrentPlayerHand();
       if(Players[CurrentDeckListLocation].CheckWin() == true){
        Players[CurrentDeckListLocation].SetWinner();
       }
        // Add file writing for Current Hand 

        //Work on Hand checking to see if have won
        //Sort out wht happens when they win
    }
    

public static void main(String[] args) {
    Scanner myObj = new Scanner(System.in);
    int realTPlayersnum = 0;
    File myObj1 = new File("");
    while (true){
        System.out.println("Please enter the number of players");
        String TPlayersnum = myObj.nextLine();
        realTPlayersnum = Integer.parseInt(TPlayersnum);
        boolean repeat = false;
        System.out.println("Please enter location of pack to load");
        String Filelocation = myObj.nextLine();
        System.out.println(Filelocation);///workspaces/Software-Development-Coursework/cardgame/Test.txt
        myObj1 = new File(Filelocation);
        int counter = 0;
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
            Players[i].PlayerWon(i);
        }  
        ThreadList[i] = new Thread(Players[i]);
        ThreadList[i].start();

    
    
    // /workspaces/Software-Development-Coursework/cardgame/Test.txt
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


    //System.out.println("Everything finished");.
    // for (int x = 0; x<ThreadList.length;x++){
    //     ThreadList[x].interrupt();
    //     System.out.println("Interrupted Thread" + x);.

    // }

    }


    
