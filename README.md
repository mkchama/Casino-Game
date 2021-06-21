// Instructions to compile: 
Compile every class using the command javac @sources.txt




// Instructions to run black jack text version: 
Run the program by using the command java BlackJack.Blackjack

// Instructions to run poker text version: 
Run the program using the command java Poker.Poker

//Instruction to run war text version: 
Run the program by using the command java War.War

// Instructions to run the gui: 
Run the program by using the command java FX.Main

// Instructions to run automated junit Tests
Compile the junit classes by running the command
javac -cp .;junit-4.12.jar;hamcrest-core-1.3.jar  ./core/*.java
Then to run use the command
java -cp .;junit-4.12.jar;hamcrest-core-1.3.jar org.junit.runner.JUnitCore core.CoreTestSuite

// Instructions to test the gui
To test the gui, run it then go into blackjack and lose as much as possbile until
your money is below 50. At this point try to bet 100$, when this fails. Bet the 
exact amount of money you have then try to double on the first command. After this round
completes click new game, your money will be reset to 500. win or lose the next round,
then exit back to the main menu. Open up war and the balance will have been transfered over.
Now run the game until you get the option to go to War. You will only be able to 
go to War if you have enough of a balance to bet twice your original bet. After the round
go back to the main menu and back into blackjack to see that the balance has 
indeed transfered over.

// Url for the gitlab
https://gitlab.cpsc.ucalgary.ca/zarif.qazi1/t11-t04-project.git