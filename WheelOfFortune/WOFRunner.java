import java.util.Scanner;
public class WOFRunner {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in); //scanner pog
        Spinner sp = new Spinner(); //spinner pog
        while (true) {
            System.out.println("Please input player 1's name: ");
            Player p1 = new Player(sc.nextLine()); //makes a player with the inputted name
            System.out.println("Hello, " + p1.getName() + ". Please input player 2's name");
            Player p2 = new Player(sc.nextLine()); //makes another player with the other inputted name
            System.out.println("Hello, " + p2.getName() + ", and welcome to CIRCLE OF LUCK! (TM)");
            Board thingy = new Board(p1, p2, sp, sc); //makes the board object
            thingy.play(); //runs the bulk of the code
            System.out.println("Replay? (Y/N)");
            String letter;
            while (true) { //runs forever until the break
                letter = sc.nextLine().toUpperCase(); //gets the letter you inputted
                if (("YN".contains(letter)) && (letter.length() == 1)) { //if it's valid and a letter:
                    break; //don't keep looping and return the letter down there 
                } //if it isn't, the loop continues
                System.out.println("Invalid input. Try inputting one of these letters: YN"); //tells you what the valid inputs are
            }
            if (letter.equals("N")) { //if they don't want to replay
                System.out.println("OK, have a nice day!"); //don't replay
                break; //break out of while loop
            } else if (letter.equals("Y")) { //if they do want to replay:
                System.out.println("Great!"); //goes back to top of while loop
            }
        }
        sc.close();
    }
}
