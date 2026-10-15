package cardgame;
import java.util.concurrent.atomic.AtomicBoolean;

public class Player implements Runnable{
    private int PlayerNum;
    private Card [] Hand;
    private static final AtomicBoolean WinnerFound = new AtomicBoolean(false);
    private CardGame Current;
    private int Actual_PlayerNum;
    
     
    
    public Player(int Pnum,CardGame Current){
        
        this.PlayerNum = Pnum;
        this.Hand = new Card[4];
        this.Current = Current;


    }
    public synchronized void addCardToHand(Card pickedupcard){
        //System.out.println("Im being ADded to " + pickedupcard.getValue());
        System.out.println(this.PlayerNum + " I have added card " +pickedupcard.getValue()+" to my hand");
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
        System.out.println(this.PlayerNum + " I have removed card "+ rCard.getValue() + " from my hand");
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
     
 


public void run() {
        System.out.println(Thread.currentThread());
        String ThreadName = Thread.currentThread().getName();
        System.out.println(ThreadName);
        String ThreaNumString = ThreadName.substring(7);
        int ThreadNum = Integer.parseInt(ThreaNumString);
        //System.out.println(ThreadNum);
        int TurnCount = 0;
        

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
                System.out.println("I'm taking a turn Player "+ PlayerNum);
                Current.PlayersTurn(ThreadNum,WinnerFound);
                }
            
                //}
                //catch(NullPointerException e){
                   // e.printStackTrace();
                //}
              if (CheckWin() == true ){//&& WinnerFound.compareAndSet(false, true)
               System.out.println("Player"+ PlayerNum + "won definetly");
               wakeWaitingthreads();

               }
            
           
        }
        System.out.println("Games over Player "+PlayerNum);
        System.out.println("I'm Player "+ PlayerNum + " I had "+ TurnCount+" turns");
        
    }
    public synchronized void wakeWaitingthreads(){
        for(int x = 0; x< CardGame.Decks.length; x++){
            synchronized(CardGame.Decks[x]){
                CardGame.Decks[x].notifyAll();
                System.out.println("I have notified"+x);
            }

        }
        
        
    }
    public synchronized boolean IsWinnerFound(){
        return WinnerFound.get();
    }
    public synchronized void SetWinner(){
        WinnerFound.set(true);
    }
}