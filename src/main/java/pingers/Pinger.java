package pingers;

import common.Result;

import java.util.Queue;

public interface Pinger {
    void ping(Queue<Result> resultQueue);
    void stop();
    Result getLastResult();
}
