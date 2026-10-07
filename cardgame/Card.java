package cardgame;
public class Card{
    private int Value;
    private int TurnsHeld;

    public Card(int newvalue){

   
    this.Value = newvalue;
    this.TurnsHeld = 0;
    }

    public synchronized int getValue(){
        return this.Value;

    }
    public synchronized int getTurnsHeld(){
        return TurnsHeld;
    }
    public synchronized void increaseTurnsHeld(){
        this.TurnsHeld = this.TurnsHeld + 1;
    }
    public synchronized void resetTurnsHeld(){
        this.TurnsHeld = 0;

    }

}