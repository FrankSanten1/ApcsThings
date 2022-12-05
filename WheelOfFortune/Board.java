import java.util.Scanner;
import java.io.File;
public class Board
{
  private Player player1; //player
  private Player player2; //player
  private Spinner wheel; //wheel
  private Scanner sc; //scanner
  
  private String realPhrase; //what the actual phrase is
  private String visiblePhrase; //what the players see of the phrase
  private String guessedLetters = ""; //which letters have already been guessed
  private Player currentPlayer; //holds the player whos turn it is
  private String consonants = "BCDFGHJKLMNPQRSTVWXYZ"; //all the consonants still available to guess
  private String vowels = "AEIOU"; //all the vowels not guessed
  public Board(Player player1Inp, Player player2Inp, Spinner wheelInp, Scanner scInp) { //constructor
    player1 = player1Inp; //makes all these obviously
    player2 = player2Inp;
    wheel = wheelInp;
    sc = scInp;
    realPhrase = randPhrase().toUpperCase(); //generates a random phrase and stores it (in uppercase)
    visiblePhrase = realPhrase; //sets visiblePhrase to realPhrase temporarily
    String letter; //temp variable
    for (int i = 0; i < visiblePhrase.length(); i++) { //runs through all of visiblePhrase
      letter = visiblePhrase.substring(i, i+1); //gets current letter
      if ((consonants + vowels).contains(letter)) { //if it's a letter, then:
        visiblePhrase = visiblePhrase.substring(0, i) + "_" + visiblePhrase.substring(i+1); //change it to an underscore tro hide it
      } //ends up with all letters turned into underscores
    }
    currentPlayer = player1; //starting player is p1
  }


  public void play() { //"main" method of this class, runs a game
    String menuChoice; //whatever the player inputs for what they're gonna do (guess con sonant, viowel, or whole word)
    String letterChoice; //whenever else you need to get a letter input that's temporary
    int timesAppeared; //when you need to store how many times a letter appeared in the phrase
    int valueSpun; //to store what the spinner got for a while

    while (!(visiblePhrase.equals(realPhrase))) {
      System.out.println(visiblePhrase); //displays phrase
      System.out.println(player1.getName() + ": $" + player1.getScore()); //displays p1 stats
      System.out.println(player2.getName() + ": $" + player2.getScore()); //displays p2 stats
      System.out.println(currentPlayer.getName() +"'s turn!"); //all the goobledygook of starting a turn: 
      System.out.println("You can:");
      System.out.println("A. Spin the wheel and guess a consonant");
      System.out.println("B. Spend 200 points to buy a vowel"); 
      System.out.println("C. Try to guess the whole phrase");
      System.out.println("Please input your choice into the terminal:");
      menuChoice = letterInp("ABC"); //gets an input (must be A, B, or C)

      if (menuChoice.equals("A")) { //consonant + wheel:
        if (consonants.equals("")) { //check to make sure that it doesn't softlock you:
          System.out.println("There aren't any consonants left to guess, try buying a vowel or guessing the phrase.");
        } else { //if there are still consonants to guess, proceed:
          System.out.println("Spinning the wheel...");
          valueSpun = wheel.spin(); //spins the wheel and stores the value
          if (valueSpun == -1) { //if the wheel landed on bankrupt: 
            System.out.println("Oh no, "+ currentPlayer.getName() +" went bankrupt. Unfortunate.");
            currentPlayer.bankrupt(); //set score to 0
            switchPlayers(); //next player's turn now
          } else { //this runs when it isn't bankrupt
            System.out.println(valueSpun + "!"); //shows what value was gotten
            System.out.println("Now, guess a consonant using the terminal.");
            System.out.println("Already guessed letters: " + guessedLetters); //shows letters already guessed so they don't guess those
            letterChoice = letterInp(consonants); //gets a guess
            consonants = consonants.replace(letterChoice, ""); //removes that letter from the available consonants to guess in the future
            timesAppeared = revealLetter(letterChoice); //reveals the letter and stores how many times it appeared
            System.out.println(letterChoice + " appeared " + timesAppeared + " time(s). You gain " + timesAppeared*valueSpun + " points."); //displays info
            currentPlayer.changeScore(timesAppeared*valueSpun); //adds that score to the player
            if (timesAppeared == 0) { //if they didn't guess right:
              System.out.println("Quite unfortunate. Now, it is on to the next player's turn.");
              switchPlayers(); //go to next player
            } //notice how if they did get one right, there is no player switch and the current player gets another round
          }
        }
      } 
      else if (menuChoice.equals("B")) { //buy vowel:
        if (vowels.equals("")) { //check to make sure that the game doesn't softlock you when asking for a vowel when all ahve been guessed already
          System.out.println("There aren't any vowels left to guess, try buying a vowel or guessing the phrase.");
        } else { //if there are still vowels to guess, run it like normal:
          if (currentPlayer.getScore() <200) { //if they don't have the requisite moolah:
            System.out.println("Sorry, you must have at least $200 to spend on buying a vowel"); //r e j e c t i o n and they go on to have another turn
          } else { //if they do indeed have the cash:
            currentPlayer.changeScore(-200);
            System.out.println("Guess a vowel using the terminal.");
            System.out.println("Already guessed letters: " + guessedLetters); //shows letters already guessed so they know not to repeat them
            letterChoice = letterInp(vowels); //gets the guess
            vowels = vowels.replace(letterChoice, ""); //removes that letter from being able to be guessed again
            timesAppeared = revealLetter(letterChoice); //reveals the letter in the word and stores the # of times it appeared
            System.out.println("The letter " + letterChoice + " appeared " + timesAppeared + " time(s) in the phrase.");
            if (timesAppeared == 0) { //if they didn't guess right:
              System.out.println("Quite unfortunate. Now, it is on to the next player's turn.");
              switchPlayers(); //next player will go
            } //notice how if they didn't get it wrong, the player stays the same and gets another round.
          }
        }
      } 
      else if (menuChoice.equals("C")) { //trying to guess the whole phrase:
        System.out.println("Guess the phrase using the terminal.");
        System.out.println(visiblePhrase); //prints the phrase with blanks for convenience
        if (sc.nextLine().toUpperCase().equals(realPhrase)) { //checks if the thing they typed is correct. if it is:
          currentPlayer.changeScore(5000); //add 5000 points to the score
          System.out.println("CORRECT! From that correct guess you gain $5000, bringing you to a total of $" + currentPlayer.getScore() + "!");
          visiblePhrase = realPhrase;
        } else { //this is for when they guess incorrectly
          System.out.println("Unfortunately, that was not the correct phrase. The turn moves on to the next player.");
          switchPlayers();
        }
      } 
      else {System.out.println("Hi, this is an error message. Something went horribly wrong if you're seeing this. (choice!=A,B,or C)");} //just in case y'know
    }

    //this part runs once the phrase is revealed and the game is finished basically
    //it just recaps the score, who won, etc.
    System.out.println("And the winner is...");
    if (player1.getScore() > player2.getScore()) { //if player 1 wins:
      System.out.println(player1.getName() +" with a total of " + player1.getScore() + " points! Congratulations!");
    } else if (player1.getScore() < player2.getScore()) { //if player2 wins:
      System.out.println(player2.getName() +" with a total of " + player2.getScore() + " points! Congratulations!");
    } else if (player1.getScore() == player2.getScore()) { //if tie:
      System.out.println("Both of you, the scores ended up tied! Congratulations!");
    } else {System.out.println("Hey, this is like, an error message, because something went funky up in this boi for this to run, so just call up Frank and tell him to fix it, now.");} //if code doesn't enjoy existence <--
    System.out.println(""); //a spacer line
    System.out.println("Final scores:");
    System.out.println(player1.getName() + ": $" + player1.getScore()); //displays p1 stats
    System.out.println(player2.getName() + ": $" + player2.getScore()); //displays p2 stats
    System.out.println("And tune in next time for another game of CIRCLE OF LUCK! (TM)");
  }

  private String letterInp(String validLetters) { //function that loops until you give it a valid input
    String letter;
    while (true) { //runs forever until the break
      letter = sc.nextLine().toUpperCase(); //gets the letter you inputted
      if ((validLetters.contains(letter)) && (letter.length() == 1)) { //if it's valid and a letter:
        break; //don't keep looping and return the letter down there 
      } //if it isn't, the loop continues
      System.out.println("Invalid input. Try inputting one of these letters: " + validLetters); //tells you what the valid inputs are
    }
    return letter; //runs after the loop, returns valid letter input
  }

  private int revealLetter(String letter) { //will reveal the iputted letter, will return the amount of times it was found
    letter = letter.toUpperCase(); //makes the letter uppercase
    guessedLetters = guessedLetters + letter; //adds the letter to the list of letters already guessed
    int numTimes = 0; //holds number of times the letter appeared
    int index; //will hold the next index of the letter in the word
    String tempPhrase = realPhrase; //will hold realPhrase, and the letters will be removed as we go
    while (!(tempPhrase.indexOf(letter) == -1)) { //while there's still the letter in tempPhrase:
      index = tempPhrase.indexOf(letter); //find the index
      visiblePhrase = visiblePhrase.substring(0, index) + letter + visiblePhrase.substring(index+1); //reveal that letter in visiblePhrase
      tempPhrase = tempPhrase.substring(0, index) + "-" + tempPhrase.substring(index+1); //replaces the letter in tempPhrase with a dash so it won't be used again
      numTimes++; //increases the number of times the letter showed up by one
    }
    return numTimes; //returns the number of letters found
  }

  private void switchPlayers() { //pretty self explanatory, switches currentPlayer from one player to the other
    if (player1 == currentPlayer) { //if it's player 1:
      currentPlayer = player2; //make it player two
    } else { //otherwise, if it's player two
      currentPlayer = player1; //make it player 1
    }
  }

  private String randPhrase(){ //voodoo magic, idk
    String tempPhrase = "";
    int numOfLines = 0;
    tempPhrase = "how are you";
    try {
      Scanner sc = new Scanner(new File(/*Replace with the path*/"/workspace/ApcsThings/WheelOfFortune/phrases.txt"));
      while (sc.hasNextLine()) {
        tempPhrase = sc.nextLine().trim();
        numOfLines++;
      }
    } catch(Exception e) { System.out.println("Error reading or parsing phrases.txt"); }
		int randomInt = (int) ((Math.random() * numOfLines) + 1);
    try {
      int count = 0;
      Scanner sc = new Scanner(new File(/*Replace with the path*/"/workspace/ApcsThings/WheelOfFortune/phrases.txt"));
      while (sc.hasNextLine()) {
        count++;
        String temp = sc.nextLine().trim(); 
        if (count == randomInt){
          tempPhrase = temp;
        }
      }
    } catch (Exception e) { System.out.println("Error reading or parsing phrases.txt"); }
    return tempPhrase;
  }

}
