package cardgame;

public class Deck {
    private int Start;
    private int End;
    private Card [] DeckQueue;
    private int DeckNum;
    private int DeckLength;

    public Deck (int DeckNum, int TotalPlayers){
        this.DeckNum = DeckNum;
        this.DeckLength = (8*TotalPlayers);
        this.DeckQueue = new Card [DeckLength];
        this.Start = 0;
        this.End = 0;
    }

    public synchronized void addCardToDeck(Card NewCard){
        this.DeckQueue[End] = NewCard;
        if (End == DeckLength-1){
            End = 0;
        }
        else{
            this.End +=1;
        }
    }
    public synchronized Card removeCardFromDeck(){
        Card PassedCard = this.DeckQueue[Start];
        this.DeckQueue[Start] = null;
        if (Start == (this.DeckLength-1)){
            int Check = 0;
        }
        else {
            int Check = Start+1;
        }
        if (this.DeckQueue[Check] != null ){
            Start = Check;
        }
        else{
            Start = 0;
        }
        return PassedCard;
    }
    public synchronized String printPlayerDeck(){
        String Message = "deck"+this.Decknum+" contents:";
        for (int i = 0;i<this.DeckLength ;i++){
            if ( this.DeckQueue[i] != null){
            Message = Message + " " + this.DeckQueue[i].getValue();
            }
        }
    } 

}