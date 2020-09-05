package agents;

import publisher.Publisher;

import java.util.Random;

public abstract class Agent {
    protected Publisher publisher;
    protected String sensorName;
    protected Random random;
    protected final long initialDelay;

    public Agent(Publisher publisher, String sensorName){
        this.publisher = publisher;
        this.sensorName = sensorName;
        this.random = new Random();
        this.initialDelay = random.nextInt(10000);
    }

    public abstract void start();
}
