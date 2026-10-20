package com.example.cardgame;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertTrue;

public class TestGameclass {
    
    static File NonRealFile = new File("NotValid.txt");// creates file object - is not real and never used
    static CardGame rand = new CardGame(4,NonRealFile);// recreates cardgame to ensure no interference with other tests
    static String ResourcesLoc = "/src/test/resources";
    static int PlayerCount =4;
    static String [] PlayerFileArray ;
    static String [] DeckFileArray ;
    static String Winner;

    @BeforeClass
    public static void setupGame() throws Exception {
        int PlayerNum = 1;
      // this removes any pre existin player files
        while (true){
            String Pfname = "Player "+PlayerNum+".txt";
            String Dfname = "Deck "+PlayerNum+".txt";
            System.out.println("I am tryin "+Pfname);
            File f = new File(Pfname);
            File Df = new File(Dfname);
            if (f.exists()){
                System.out.println("Iworked");
                f.delete();
                Df.delete();
                PlayerNum +=1;
            }
            else{
                break;
            }


        }

        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;
        PlayerFileArray = new String[PlayerCount];
        DeckFileArray = new String[PlayerCount];
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //int PlayerCount = 4;
        try {

            File file = new File(TestGameclass.class.getResource("/Test.txt").toURI());
        String input ="4\n" +file.getAbsolutePath() + "\n";

            System.setIn(new ByteArrayInputStream(
                input.getBytes(StandardCharsets.UTF_8)
            ));

            System.setOut(new PrintStream(out));

            // Execute the actual main method
            Player test47 =(new Player(4,rand));
            System.out.println(test47.IsWinnerFound()+"WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
            CardGame.main(new String[]{});

        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        String output = out.toString(StandardCharsets.UTF_8.name());

        // Verify that main requested both inputs
        assertTrue(output.contains(
            "Please enter the number of players"
        ));

        assertTrue(output.contains("Please enter location of pack to load"));
        System.out.println("I worked");
        System.out.println(output);
        for(int o = 1;o<5;o++){
            String PlayerFileName = "Player "+o+".txt";
            String DeckFileName = "Deck "+o+".txt";

                String TempString1 ="";
                String line;
                BufferedReader Out1= new BufferedReader(new FileReader(PlayerFileName));
                while ((line = Out1.readLine()) != null) {
                    TempString1 = TempString1+ line + "\n";
                    if (line.contains("Wins")){
                        Winner = line;
                    }
                }
                Out1.close();
                String TempString2 ="";
                String line2;
                BufferedReader Out2= new BufferedReader(new FileReader(DeckFileName));
                while ((line2 = Out2.readLine()) != null) {
                   TempString2 = TempString2+ line2 + "\n";
                }
                Out2.close();
                PlayerFileArray[o-1] = TempString1;
                DeckFileArray[o-1] = TempString2;
            System.out.println(PlayerFileArray[o-1] + "\n\nHello\n\n");
        }

    }
    @Test
    public void WinnerOutputted(){
        for(int i =0;i<this.PlayerCount;i++){
            assertTrue(this.PlayerFileArray[i].contains(Winner));
        }
    } 

    @Test
    public void initialHandOutputted(){
        for(int i =0;i<this.PlayerCount;i++){
            assertTrue(this.PlayerFileArray[i].contains("Intial Hand:  Player "+(i+1)+" Current Hand: "));
        }
    }
    @Test
    public void FinalCardCOunt(){
        int TotalCardCount =0;
        int CardsinDeck = 0;
        for(int i=0; i<PlayerCount;i++){
            String TempPlayer = PlayerFileArray[i].lines().filter(q -> q.contains("Final Hand")).findFirst().orElse("");
            //String TempDeck = DeckFileArray[i].lines().contains("Deck"+(i+1)+" contents:");
            // TempPlayer = TempPlayer.split(":")[1];
            TempPlayer = TempPlayer.split("Final Hand: Player "+(i+1)+" Current Hand: ")[1];
            String [] PlayerHand = TempPlayer.split(" ");
            //System.out.println(PlayerHand.length);
            int PlayerHandCount = 0;
            for (int q =0;q<PlayerHand.length;q++){
                if(PlayerHand[q] != ""){
                    //System.out.println(PlayerHand[q]);
                    PlayerHandCount+=1;
                    
                }
            }
            assertTrue(PlayerHandCount == 4); // checks 4 cards in each hand
            TotalCardCount = TotalCardCount +PlayerHandCount;

            String TempDeck = DeckFileArray[i].lines().filter(q -> q.contains("contents: ")).findFirst().orElse("");
            //String TempDeck = DeckFileArray[i].lines().contains("Deck"+(i+1)+" contents:");
            // TempPlayer = TempPlayer.split(":")[1];
            String [] TempDeckArray = TempDeck.split(" contents:");
            //System.out.println(TempDeck);
            if(TempDeckArray.length != 1){
                TempDeck =TempDeckArray[1];
                //System.out.println(TempDeck);
                String [] PlayerDeck = TempDeck.split(" ");
                //System.out.println(PlayerDeck.length);
                for (int q =0;q<PlayerDeck.length;q++){
                    if(PlayerDeck[q] !=""){
                        //System.out.println(PlayerDeck[q] + " I counted");
                        CardsinDeck+=1;
                    }
                }
            }
        }
        //System.out.println(CardsinDeck);
        assertTrue(CardsinDeck == (4*PlayerCount)); // checks there are 16 cards in Decks
        TotalCardCount = TotalCardCount + CardsinDeck;
        assertTrue(TotalCardCount == (8*PlayerCount)); // checks there are correct amount overall
    }
}
