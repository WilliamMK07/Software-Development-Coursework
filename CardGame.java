import cardgame;
import java.io.File;                  
import java.io.FileNotFoundException; 
import java.util.Scanner;

public class CardGame{
    private int TotalPlayersnum;
    private File CardPackFile;

    public CardGame(int ChosenPlayerNum, File Cpackfile){
        this.TotalPlayersnum = ChosenPlayerNum;
        this.CardPackFile = Cpackfile;
    }
    
    public boolean readcardpack(){
        boolean ValidCardPack = true;
        
        int [] CardValuearray = new int[8*this.TotalPlayersnum];
        try (Scanner myReader = new Scanner(this.CardPackFile)){
            while(myReader.hasNextLine()){
                boolean ItemAdded = false;
                String Value = myReader.nextLine();
                int num = Value;
                for (int i=0; i<CardValuearray.length;i++){
                    if (CardValuearray[i] = null){
                        CardValuearray[i] = num;
                        ItemAdded = true;

                    }
                

                }
                
                
            }
        for (int x=0; x<CardValuearray.length;x++){
            if (CardValuearray[])
        }
        





    }

}

public static void main(String[] args) {
    Scanner myObj = new Scanner(System.in);
    System.out.println("Please enter the number of players");
    String TPlayersnum = myObj.nextLine();
    Int realTPlayersnum = TPlayersnum;
    System.out.println("Please enter location of pack to load");
    String Filelocation = myObj.nextLine();
    File Cardpack = new File(Filelocation);
 }
}