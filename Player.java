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
            else{
                System.out.println("");
            }
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
        return rCard;
    

    }
    
}