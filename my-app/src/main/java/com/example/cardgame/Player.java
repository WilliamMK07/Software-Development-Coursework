package com.example.cardgame;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;


public class Player implements Runnable{
    private int PlayerNum;
    private Card [] Hand;
    private static final AtomicBoolean WinnerFound = new AtomicBoolean(false);
    private CardGame Current;
    private static final AtomicInteger Winnernum = new AtomicInteger(0);
    public File PlayerFile;
    public String Filename;
    
     
    
    public Player(int Pnum,CardGame Current) {
        
        this.PlayerNum = Pnum;
        this.Hand = new Card[4];
        this.Current = Current;
        this.PlayerFile = new File("Player " +(this.PlayerNum+1) + ".txt");
        this.Filename = "Player " +this.PlayerNum + ".txt";


    }
    public synchronized void addCardToHand(Card pickedupcard){
        //System.out.println("Im being ADded to " + pickedupcard.getValue());
        //System.out.println(this.PlayerNum + " I have added card " +pickedupcard.getValue()+" to my hand");
        for(int i= 0; i <this.Hand.length; i++){
            if (this.Hand[i] == (null)){
                this.Hand[i] = pickedupcard;
                break;
            }
        }
    }
    
    public synchronized String showCurrentPlayerHand(){
        String Returnstring = " ";
        Returnstring = "Player " + this.PlayerNum + " Current Hand: ";
        for (int x =0; x <this.Hand.length; x++){
            if (this.Hand[x] != (null)){
            Returnstring = Returnstring +" " + this.Hand[x].getValue();
            }
        }
        
        return Returnstring;

    }
    public synchronized Card CardToBeRemoved(){
        for (int u =0; u <this.Hand.length; u++){
            this.Hand[u].increaseTurnsHeld();
        }
        int highestTurnsHeld = 0;
        int currentCardToBeRemoved = 0;
        for (int y =0; y <this.Hand.length; y++){
            if (this.Hand[y].getValue() != this.PlayerNum){
                if (this.Hand[y].getTurnsHeld() > highestTurnsHeld){
                    highestTurnsHeld = this.Hand[y].getTurnsHeld();
                    currentCardToBeRemoved = y;
                }
                
            }
        }
        this.Hand[currentCardToBeRemoved].resetTurnsHeld();
        Card rCard = this.Hand[currentCardToBeRemoved];
        this.Hand[currentCardToBeRemoved] = null;
        // System.out.println(this.PlayerNum + " I have removed card "+ rCard.getValue() + " from my hand");
        return rCard;
    

    }
 
    
    public synchronized boolean CheckWin(){
        if (this.Hand[0] == null){
            return false;
        }
        int firstnum = this.Hand[0].getValue();
        for (int x = 0; x <this.Hand.length; x++){
            
            
            if (this.Hand[x] == null|| this.Hand[x].getValue() != firstnum ){
                return false;

            }


        }
        return true;

    }
    public synchronized void PlayerWon(int WinnerPlayerNum){
        System.out.println("Player "+ WinnerPlayerNum + " Wins ");
        Winnernum.set(WinnerPlayerNum);       
        wakeWaitingthreads();
        for(int x=0;x<CardGame.getTotalPlayersnum();x++){
            System.out.println("Player " + x + ": " + CardGame.Players[x].showCurrentPlayerHand() + CardGame.Decks[x].printPlayerDeck()); 
            try{
                BufferedWriter writer = new BufferedWriter(new FileWriter(CardGame.Players[x].Filename, true));
 
                writer.newLine();
                writer.write("Player " + WinnerPlayerNum + " Wins");
                writer.newLine();
                writer.close();
            }
            catch(IOException e){
                System.out.println("File writing error");

            }
        }


        }
    
     
 


public void run() {
        System.out.println(Thread.currentThread());
        String ThreadName = Thread.currentThread().getName();
        // System.out.println(ThreadName);
        // String ThreaNumString = ThreadName.substring(7);
        int ThreadNum = this.PlayerNum-1;
        //System.out.println(ThreadNum);
        int TurnCount = 0;
        if(CheckWin() == true){
            WinnerFound.set(true);
            //PlayerWon(PlayerNum);
            
        }
        while(WinnerFound.get() == false){
            
            // System.out.println("I'm system "+ ThreadNum);
            // if (ThreadNum == 3){
            //     System3(3);
            //     try{
            //     Thread.sleep(1000000000);
            //     }
            //     catch(InterruptedException e){

            //     }
            // }
            if(WinnerFound.get() == true){
                break;
            }
            
                //try{
            if(CardGame.Decks[ThreadNum].CheckDeck() == true){
                TurnCount+=1;
                //System.out.println("I'm taking a turn Player "+ PlayerNum);
                Current.PlayersTurn(ThreadNum,WinnerFound);
            }
            
                //}
                //catch(NullPointerException e){
                   // e.printStackTrace();
                //}
            if (CheckWin() == true ){//&& WinnerFound.compareAndSet(false, true)
                PlayerWon(PlayerNum);
            }
           
        }
        // System.out.println("I'm Player "+ PlayerNum + " I had "+ TurnCount+" turns");
        // System.out.println("Player Exits "+PlayerNum);
        
          try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(this.Filename, true));
            if(PlayerNum != Winnernum.get()){
                writer.newLine();
                writer.write("Player " + Winnernum.get() +" informs player " + PlayerNum + " that Player " + Winnernum.get() + " has won. " );
            }
        
            writer.newLine();
            writer.write("Player " + PlayerNum + " Exits");
            writer.close();
          }
          
          catch(IOException e){
            System.out.println("File writing error");

          }
        }
    
        
    public synchronized void wakeWaitingthreads(){
        for(int x = 0; x< CardGame.Decks.length; x++){
            synchronized(CardGame.Decks[x]){
                CardGame.Decks[x].notifyAll();
                // System.out.println("I have notified"+x);
            }

        }
        
        
    }
    public synchronized boolean IsWinnerFound(){
        return WinnerFound.get();
    }
    public synchronized void SetWinner(){
        WinnerFound.set(true);
        Winnernum.set(PlayerNum);
    }
    public Card [] getHand(){
        return this.Hand;
    }
    public boolean isHandEmpty() {
        int Count = 0;
        for (int r = 0; r<CardGame.getTotalPlayersnum();r++){
            if(this.Hand[r] != null){
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
}