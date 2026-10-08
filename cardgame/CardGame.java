package cardgame;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class CardGame implements Runnable{
    private int TotalPlayersnum;
    private File CardPackFile;
    private Card [] AllCards;
    private Player [] Players;
    private Deck [] Decks;

    public CardGame(int ChosenPlayerNum, File Cpackfile){
        this.TotalPlayersnum = ChosenPlayerNum;
        this.CardPackFile = Cpackfile;
        this.AllCards = new Card[8*ChosenPlayerNum];
        this.Players = new Player[ChosenPlayerNum];
        this.Decks = new Deck[ChosenPlayerNum];
    }
    
    public boolean readcardpack(){
        int [] CardValuearray = new int[8*this.TotalPlayersnum];
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
    catch(Exception FileNotFoundException){
        System.out.println("No file entered");
        return(false);
    }
    return(true);
    }
    public void distrubuteCards(){
        
        for(int x=0;x<TotalPlayersnum;x++){
            Players[x] = new Player(x+1);
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
        // for(int x=0;x<TotalPlayersnum;x++){
        //   System.out.println("Player " + x + ": " + Players[x].showCurrentPlayerHand() + Decks[x].printPlayerDeck()); 
        // }
    }
    public void PlayersTurn(int PlayerNum){
        int NextDeckListLocation = PlayerNum;
        int CurrentPlayerListLocation = PlayerNum-1;
        int CurrentDeckListLocation = PlayerNum-1;
        if (PlayerNum == this.TotalPlayersnum){
             NextDeckListLocation = 0;
        }
        else{
             NextDeckListLocation = PlayerNum;
        }
        
        Decks[NextDeckListLocation].addCardToDeck(Players[CurrentPlayerListLocation].CardToBeRemoved());
        // Add FIle writing for discard here
        Players[CurrentPlayerListLocation].addCardToHand(Decks[CurrentDeckListLocation].removeCardFromDeck());
        // Add file writing for adding to hand here
        System.out.println("Hand: "+ Players[CurrentPlayerListLocation].showCurrentPlayerHand() +"DEck: "+Decks[CurrentDeckListLocation].printPlayerDeck());
        Players[CurrentPlayerListLocation].showCurrentPlayerHand();
        // Add file writing for Current Hand 

        //Work on Hand checking to see if have won
        //Sort out wht happens when they win
    }


public static void main(String[] args) {
    Scanner myObj = new Scanner(System.in);
    System.out.println("Please enter the number of players");
    String TPlayersnum = myObj.nextLine();
    int realTPlayersnum = Integer.parseInt(TPlayersnum);
    File myObj1 = new File("");
    while (true){
        System.out.println("Please enter location of pack to load");
        String Filelocation = myObj.nextLine();
        myObj1 = new File(Filelocation);
        int counter = 0;
        // try-with-resources: Scanner will be closed automatically
    try (Scanner myReader = new Scanner(myObj1)) {
        while (myReader.hasNextLine()) {
            String data = myReader.nextLine();
            System.out.println(data);
            counter +=1;
        }
    } catch (FileNotFoundException e) {
      System.out.println("An error occurred.");
      e.printStackTrace();
    }
        System.out.println(counter);
        if(counter == (realTPlayersnum*8))
            break;
    }
    CardGame Current = new CardGame(realTPlayersnum,myObj1);
    Current.readcardpack();
    Current.distrubuteCards();
    
    Thread [] ThreadArray = new Thread[realTPlayersnum];
    for (int i = 0; i<realTPlayersnum;i++){
        ThreadArray[i] = new Thread(Current);
        ThreadArray[i].start();
    // }/workspaces/Software-Development-Coursework/cardgame/Test.txt
    }
    Thread chill= new Thread(Current);
    chill.start();
}
    public void run() {
        System.out.println(Thread.currentThread());
        String ThreadName = Thread.currentThread().getName();
        System.out.println(ThreadName);
        String ThreaNumString = ThreadName.substring(7);
        int ThreadNum = Integer.parseInt(ThreaNumString);
        System.out.println(ThreadNum);
        int TurnCount = 0;
        while(true){
            PlayersTurn(ThreadNum);
            TurnCount +=1;
            if (TurnCount ==10){
                break;
            } 
        }
    }
}