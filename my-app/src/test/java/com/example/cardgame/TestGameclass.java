
package com.example.cardgame;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class TestGameclass {

    @Test
    public void setupGame() {
        // Save the original input and output streams
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            // Simulate the two user inputs
            System.setIn(new ByteArrayInputStream(
                "4\nTest.txt\n".getBytes()
            ));

            // Capture the program's output
            System.setOut(new PrintStream(out));

            // Run the main method
            CardGame.main(new String[]{});

        } finally {
            // Restore the original streams
            System.setIn(originalIn);
            System.setOut(originalOut);
        }

        // Check the output after running the program
        String output = out.toString();

        assertTrue(output.contains(
            "Please enter the number of players"
        ));
    }
}
