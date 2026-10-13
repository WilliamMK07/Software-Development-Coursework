package cardgame;

public class Deck {
    private int Start;
    private int End;
    private Card [] DeckQueue;
    private int DeckNum;
    private int DeckLength;
    private int Decksize = 0;

    public Deck (int DeckNum, int TotalPlayers){
        this.DeckNum = DeckNum;
        this.DeckLength = (8*TotalPlayers);
        this.DeckQueue = new Card [DeckLength];
        this.Start = 0;
        this.End = 0;
    }

    public synchronized void addCardToDeck(Card NewCard){
       



        this.DeckQueue[End] = NewCard;
        this.Decksize += 1;
        End = (End + 1) % DeckLength;
        System.out.println("notifying that Player" + DeckNum + " can play again");
        
        notify();
        
        // if (End == DeckLength-1){
        //     End = 0;
        // }
        // else{
        //     this.End +=1;
        // }
    }
    public synchronized Card removeCardFromDeck(){
        while (this.Decksize == 0){


            try{
                System.out.println("Player" + DeckNum + " can no longer go as the deck is empty so is being frozen out");
            wait();
             System.out.println("Player" + DeckNum + " can no longer go as the deck is empty so is being frozen out");
            }
            catch(InterruptedException e) {}
        }
        Card PassedCard = this.DeckQueue[Start];
        this.DeckQueue[Start] = null;
        this.Decksize -= 1;
        Start = (Start + 1) % DeckLength;
        // int Check = 0;
        // if (Start == (this.DeckLength-1)){
        //     Check = 0;
        // }
        // else {
        //      Check = Start+1;
        // }
        // if (this.DeckQueue[Check] != null ){
        //     Start = Check;
        // }
        // else{
        //     Start = 0;
        // }
        System.out.println(this.DeckNum +" I'm removing card "+ PassedCard.getValue()+" To be added to hand");
        
        return PassedCard;
    }
    public synchronized String printPlayerDeck(){
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
    
}