package cardgame;

public class Player implements Runnable{
    private int PlayerNum;
    private Card [] Hand;
    public volatile boolean WinnerFound = false;
    
     
    
    public Player(int Pnum){
        
        this.PlayerNum = Pnum;
        this.Hand = new Card[4];



    }
    public synchronized void addCardToHand(Card pickedupcard){
        //System.out.println("Im being ADded to " + pickedupcard.getValue());
        System.out.println(this.PlayerNum + " I have added card " +pickedupcard.getValue());
        for(int i= 0; i <this.Hand.length; i++){
            if (this.Hand[i] == (null)){
                this.Hand[i] = pickedupcard;
                break;
            }
        }
    }
    
    public synchronized String showCurrentPlayerHand(){
        String Returnstring = " ";
        Returnstring = "Player " + this.PlayerNum + " Current Hand";
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
        System.out.println(this.PlayerNum + " I have removed card "+ rCard.getValue() + " from hand");
        return rCard;
    

    }
    public synchronized void PlayersTurn(int PlayerNum){
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
        CardGame.Decks[NextDeckListLocation].addCardToDeck(CardGame.Players[CurrentPlayerListLocation].CardToBeRemoved());
        // Add FIle writing for discard here
        System.out.println(CurrentDeckListLocation + " I've removed a card to "+ PlayerNum);
        
        
        CardGame.Players[CurrentPlayerListLocation].addCardToHand(CardGame.Decks[CurrentDeckListLocation].removeCardFromDeck());
        // Add file writing for adding to hand here
        System.out.println(CurrentDeckListLocation + " I've added a card to " + PlayerNum);
        
        System.out.println("Hand: "+ CardGame.Players[CurrentPlayerListLocation].showCurrentPlayerHand() +"DEck: "+CardGame.Decks[CurrentDeckListLocation].printPlayerDeck() + CardGame.Decks[NextDeckListLocation].printPlayerDeck());
       CardGame.Players[CurrentPlayerListLocation].showCurrentPlayerHand();
        // Add file writing for Current Hand 

        //Work on Hand checking to see if have won
        //Sort out wht happens when they win
        
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
        System.out.println("Player" + PlayerNum + "won");
        return true;

    }


public void run() {
        System.out.println(Thread.currentThread());
        String ThreadName = Thread.currentThread().getName();
        System.out.println(ThreadName);
        String ThreaNumString = ThreadName.substring(7);
        int ThreadNum = Integer.parseInt(ThreaNumString);
        //System.out.println(ThreadNum);
        int TurnCount = 0;
        

        while(WinnerFound == false){
            // System.out.println("I'm system "+ ThreadNum);
            // if (ThreadNum == 3){
            //     System3(3);
            //     try{
            //     Thread.sleep(1000000000);
            //     }
            //     catch(InterruptedException e){

            //     }
            // }
            
                try{
                PlayersTurn(ThreadNum);
                }
                catch(NullPointerException e){
                    
                }
              if (CheckWin() == true){
               WinnerFound = true;
               }
            
           
        }
        System.out.println("Games over");
        
        
    }
}