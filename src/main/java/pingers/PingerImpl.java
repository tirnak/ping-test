package pingers;

import common.Result;

import java.util.Date;
import java.util.Queue;

abstract class PingerImpl implements Pinger {

    protected static final String SPACE = " ";

    protected final String host;
    protected final String options;
    private final int delay;

    public PingerImpl(String host, String options, int delay) {
        this.host = host;
        this.options = options;
        this.delay = delay;
    }

    protected volatile Result lastResult = new Result("",true,new Date());
    private volatile boolean active = true;

    public void ping(Queue<Result> resultQueue) {
        new Thread(() -> {
            while (active) {
                new Thread(() -> {
                    Result result = innerPing();
                    resultQueue.add(result);
                    lastResult = result;
                }).start();
                try {
                    Thread.sleep(delay * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    //TODO improve catch handling
                }
            }
        }).start();
    }

    protected abstract Result innerPing();

    public void stop() {
        active = false;
    }

    public Result getLastResult() { return lastResult; }

}
