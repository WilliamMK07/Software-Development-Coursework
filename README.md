
Requirements to run the program and test suite:

Modern IDE or a terminal to execute the program that has a modern version of the Java development kit ideally 8 or higher to make.Available to download at https://www.oracle.com/uk/java/technologies/downloads/. 
A modern maven version which is downloaded for example maven 3.9 or 3.10 but not maven 4.0. This will be downloaded differently depending on the operating system but for windows you want to download the Apache Maven zip of one of the new stable versions Unzip it then add the bin directory of the created directory of the recent version of Maven to the PATH environment variable. More in depth description on Mavens website https://maven.apache.org/install.html.

JUnit 4.13.2 version as supplied by the jav files in the test directory inside the my-app directory. 

How to run the main program:

first you must place a suitable card pack inside the same directory as the cards.jar file. Then use cd to navigate to the main Software-Development-coursework folder then you must run the jar file inside the correct directory where it is placed using this command: java -jar cards.jar in the terminal of the IDE or computers termninal. You then enter the correct cardpack file  and number of players to run the game.

How to run the testing suite:

You must unzip the card test.zip folder inside the Software-Development-Coursework/main folder that holds all code and documents. If you use the cd command to navigate to the my-app folder inside the Cardtest.zip file the terminal of your chosen IDE or in your computer systems terminal.
use this command to naviagate into my-app where the test suite can be run:
cd my-app 
then use this command to run the test suite which will run all the test classes:
 mvn clean test
 and the results of the test suite will be shown. It will show one error from TestGameClass.java. This is due to the static variable WinnerFound in the player class being set to true in a previous test. To run this test class independently to prove the test works type:  mvn test -Dtest=TestGameclass in the same directory. 
 If you wanted to run the tests specifically from the test suite 
 mvn test -Dtest=CardGameTestSuite.Java
 

