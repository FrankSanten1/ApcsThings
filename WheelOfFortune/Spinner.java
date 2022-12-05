public class Spinner {
    public Spinner() {}
    public int spin() {
        int value = 0;
        double rand = Math.random();
        if (rand <0.1) {
            value = 500;
        } else if (rand <0.2) {
            value = 550;
        } else if (rand <0.3) {
            value = 600;
        } else if (rand <0.4) {
            value = 650;
        } else if (rand <0.5) {
            value = 700;
        } else if (rand <0.6) {
            value = 750;
        } else if (rand <0.7) {
            value = 800;
        } else if (rand <0.8) {
            value = 850;
        } else if (rand <0.9) {
            value = 900;
        } else {
            value = -1;
        }
        return value;
    }
}
