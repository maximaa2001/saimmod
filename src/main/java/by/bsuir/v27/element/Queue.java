package by.bsuir.v27.element;

public class Queue {
    private int limit;
    private int state;

    public Queue(int limit) {
        this.limit = limit;
        state = 0;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public int getLimit() {
        return limit;
    }
}
