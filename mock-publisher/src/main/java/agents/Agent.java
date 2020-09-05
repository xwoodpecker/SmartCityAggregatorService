package agents;

import publisher.Publisher;

import java.util.Random;

/**
 * The type Agent.
 */
public abstract class Agent {
    /**
     * The Publisher.
     */
    protected Publisher publisher;
    /**
     * The Sensor name.
     */
    protected String sensorName;
    /**
     * The Random.
     */
    protected Random random;
    /**
     * The Initial delay.
     */
    protected final long initialDelay;

    /**
     * Instantiates a new Agent.
     *
     * @param publisher  the publisher
     * @param sensorName the sensor name
     */
    public Agent(Publisher publisher, String sensorName){
        this.publisher = publisher;
        this.sensorName = sensorName;
        this.random = new Random();
        this.initialDelay = random.nextInt(10000);
    }

    /**
     * Start.
     */
    public abstract void start();
}
