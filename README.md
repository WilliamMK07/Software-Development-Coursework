
Requirements to run the program and test suite:
Modern IDE or a terminal to execute the program that has a modern version of the Java development kit ideally 8 or higher to make . A modern maven version which  downloaded for example maven 3.9 or 3.10 but not maven 4.0. This will be downloaded when running the command mvn clean test in the my-app folder of the extracted Cardtest.zip file.
JUnit 4.13.2 version as supplied by the jav files in the test directory inside the my-app directory. 
How to run the main program: first you must place a suitable card pack inside the same directory as the cards.jar file. Then use cd to navigate to Cardtest folder Then you must run the jar file inside the correct directory where it is placed using this command: java -jar cards.jar in the terminal of the IDE or  You then enter the correct cardpack file  and number of players to run the game.
How to run the testing suite:
If you are opening the card test.zip folder If you use the cd command to navigate to the my-app folder in the terminal of your chosen IDE or in your computer systems terminal.
use this command to naviagate into my-app where the test suite can be run:
cd my-app 
then use this command to run the test suite which will run all the test classes:
 mvn clean test
 and the results of the test suite will be shown.

