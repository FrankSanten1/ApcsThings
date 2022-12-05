public class Player {
    private String name;
    private int score;
    public Player(String nameInp) {
        name = nameInp;
        score = 0;
    }
    public String getName() {return name;}
    public int getScore() {return score;}
    public void setName(String newName) {name = newName;}
    public void setScore(int newScore) {score = newScore;} 
    public void changeScore(int X) {score += X;}
    public void bankrupt() {score = 0;}
}
