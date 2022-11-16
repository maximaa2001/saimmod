package by.bsuir.v27.smo;

import by.bsuir.v27.State;
import by.bsuir.v27.element.Channel;
import by.bsuir.v27.element.Queue;
import by.bsuir.v27.element.Source;
import by.bsuir.v27.generator.Generator;

import java.util.HashMap;
import java.util.Map;

public class SMO {
    private final Channel channel2;
    private final Queue queue;
    private final Channel channel1;
    private final Source source;
    private final Generator generator = Generator.getInstance();
    private final Map<State, Integer> countStates = new HashMap<>();
    private int rejectedCount = 0;
    private int sourceGeneratedCount = 0;
    private int finishCount = 0;
    private int chanel1State = 0; // K1
    private int chanel2State = 0; //K2
    private int queueLength = 0;
    private int systemLength = 0;

    public SMO(double pi1, double pi2, int limitQueue) {
        source = new Source();
        channel1 = new Channel(pi1);
        queue = new Queue(limitQueue);
        channel2 = new Channel(pi2);
    }

    public void nextStep() {
        double number1 = generator.generateNumber();
        if (channel2.getState() == 1) {
            chanel2State++;
            if ((1 - channel2.getPi()) > number1) {
                channel2.setState(0);
                finishCount++;
                if (queue.getState() > 0) {
                    queue.setState(queue.getState() - 1);
                    channel2.setState(1);
                }
            }

        }


        double number2 = generator.generateNumber();
        if (channel1.getState() == 1) {
            chanel1State++;
            if((1 - channel1.getPi()) > number2) {
                if (queue.getState() < queue.getLimit()) {
                    channel1.setState(0);
                    queue.setState(queue.getState() + 1);
                } else {
                    rejectedCount++;
                }
            }

        }

        if (queue.getState() > 0 && channel2.getState() == 0) {
            queue.setState(queue.getState() - 1);
            channel2.setState(1);
        }

        switch (source.getState()) {
            case 2:
                source.setState(1);
                break;
            case 1:
                source.setState(2);
                sourceGeneratedCount++;
                if (channel1.getState() == 0) {
                    channel1.setState(1);
                } else {
                    rejectedCount++;
                }
                break;
        }
        queueLength += queue.getState();
        systemLength += channel1.getState() + queue.getState() + channel2.getState();
        saveState();
    }

    public Map<State, Integer> getCountStates() {
        return countStates;
    }

    public int getRejectedCount() {
        return rejectedCount;
    }

    public int getSourceGeneratedCount() {
        return sourceGeneratedCount;
    }

    public int getFinishCount() {
        return finishCount;
    }

    public int getChanel1State() {
        return chanel1State;
    }

    public int getChanel2State() {
        return chanel2State;
    }

    public int getQueueLength() {
        return queueLength;
    }

    public int getSystemLength() {
        return systemLength;
    }

    private void saveState() {
        State state = new State(source.getState(), channel1.getState(), queue.getState(), channel2.getState());
        Integer count = countStates.get(state);
        if (count == null) {
            countStates.put(state, 1);
        } else {
            countStates.put(state, ++count);
        }
    }
}
