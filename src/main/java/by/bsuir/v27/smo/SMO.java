package by.bsuir.v27.smo;

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

    public SMO(double pi1, double pi2, int limitQueue) {
        source = new Source();
        channel1 = new Channel(pi1);
        queue = new Queue(limitQueue);
        channel2 = new Channel(pi2);
    }

    public void nextStep() {
        if (channel2.getState() == 1 && (1 - channel2.getPi()) > generator.generateNumber()) {
            channel2.setState(0);
        }

        if (channel2.getState() == 0 && queue.getState() > 0) {
            queue.setState(queue.getState() - 1);
            channel2.setState(1);
        }

        if (channel1.getState() == 1 && (1 - channel1.getPi()) > generator.generateNumber()) {
            channel1.setState(0);
            if (queue.getState() < 2) {
                queue.setState(queue.getState() + 1);
                if(channel2.getState() == 0) {
                    channel2.setState(1);
                    queue.setState(queue.getState() - 1);
                }
            } else {
                rejectedCount++;
            }
        }

        switch (source.getState()) {
            case 2:
                source.setState(1);
                break;
            case 1:
                source.setState(2);
                if (channel1.getState() == 0) {
                    channel1.setState(1);
                } else {
                    rejectedCount++;
                }
                break;
        }
        saveState();
    }

    public Map<State, Integer> getCountStates() {
        return countStates;
    }

    public int getRejectedCount() {
        return rejectedCount;
    }

    private void saveState() {
        State state = new State(source.getState(), channel1.getState(), queue.getState(), channel2.getState());
        Integer count = countStates.get(state);
        if(count == null) {
            countStates.put(state, 1);
        } else {
            countStates.put(state, ++count);
        }
    }
}
