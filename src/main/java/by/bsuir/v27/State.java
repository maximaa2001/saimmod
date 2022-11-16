package by.bsuir.v27;

import java.util.Objects;

public class State {
    private int sourceState;
    private int channel1State;
    private int queueState;
    private int channel2State;

    public State(int sourceState, int channel1State, int queueState, int channel2State) {
        this.sourceState = sourceState;
        this.channel1State = channel1State;
        this.queueState = queueState;
        this.channel2State = channel2State;
    }

    public int getChannel1State() {
        return channel1State;
    }

    public int getChannel2State() {
        return channel2State;
    }

    public int getQueueState() {
        return queueState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return sourceState == state.sourceState && channel1State == state.channel1State && queueState == state.queueState && channel2State == state.channel2State;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceState, channel1State, queueState, channel2State);
    }

    @Override
    public String toString() {
        return Integer.toString(sourceState) + Integer.toString(channel1State) + Integer.toString(queueState) + Integer.toString(channel2State);
    }
}
