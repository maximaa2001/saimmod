package by.bsuir.v27.element;

public class Channel {
    private double pi;
    private int state;

    public Channel(double pi) {
        this.pi = pi;
        state = 0;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public double getPi() {
        return pi;
    }
}
