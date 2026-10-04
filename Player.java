package cardgame;

public class Player{
    private int PlayerNum;
    private Card [] Hand;
    
     
    
    public Player(int Pnum){
        
        this.PlayerNum = Pnum;
        this.Hand = new Card[4];



    }
    public synchronized void addCardToHand(Card pickedupcard){
        for(int i= 0; i <this.Hand.length; i++){
            if (this.Hand[i] == (null)){
                this.Hand[i] = pickedupcard;
            }
        }
    
    public synchronized String showCurrentPlayerHand(){
        String Returnstring = " ";
        Returnstring = "Player" + this.PlayerNum + "Current Hand";
        for (int x =0; x <this.Hand.length; x++){
            Returnstring = Returnstring +" " + this.Hand[x].getValue;
        }
        
        return Returnstring;

    }
    public synchronized Card(){
        for (int u =0; u <this.Hand.length; u++){
            this.Hand[u].increaseTurnsHeld();
        }
        int highestTurnsHeld = 0;
        int currentCardToBeRemoved = 0;
        for (int y =0; y <this.Hand.length; y++){
            if (this.Hand[y].getValue != this.PlayerNum){
                if (this.Hand[y].TurnsHeld > highestTurnsHeld){
                    highestTurnsHeld = this.Hand[y].TurnsHeld;
                    currentCardToBeRemoved = y;
                }
                
            }
        }
        this.Hand[currentCardToBeRemoved].TurnsHeld = 0;
        rCard = this.Hand[currentCardToBeRemoved];
        this.Hand[currentCardToBeRemoved] = null;
        return rCard;
    }

    }
    
}