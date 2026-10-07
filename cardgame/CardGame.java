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
                        CardValuearray[i] = num;
                        ItemAdded = true;

                    }
                

                }
                if (ItemAdded == false){
                    return false; // Checks if file is too large eg 8n+1
                }
                
            }
        for (int x=0; x<CardValuearray.length;x++){
            if (CardValuearray[x] == -1){
                return false; // Checks if file is too small eg 8n-1
            }
            else{
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
            Players[x] = new Player(x);
            Decks[x] = new Deck(x,this.TotalPlayersnum);
        }
        int CurrentPlayer = 0;
        for(int i = 0; i<AllCards.length;i++){
            if(i<this.TotalPlayersnum*4){
                Players[CurrentPlayer].addCardToHand(AllCards[i]);
            }
            else{
                Decks[CurrentPlayer].addCardToDeck(AllCards[i]);

            }
            if CurrentPlayer ==(this.TotalPlayersnum-1){
                CurrentPlayer = 0;
            }
            else{
                CurrentPlayer +=1;
            }
        }
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
    System.out.println("Please enter location of pack to load");
    String Filelocation = myObj.nextLine();
    File Cardpack = new File(Filelocation);
 }
}