package common;

import pingers.Pinger;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PingJob {
    private String host;
    private Pinger icpmPinger;
    private Pinger httpPinger;
    private Pinger traceroutePinger;
    private Queue<Result> resultQueue;
    private Reporter reporter;

    public void run() {
        new Thread(() -> {
            while (true) {
                Result last = resultQueue.poll();
                if (last != null && !last.isSuccessful) {
                    Map<String, String> report = new HashMap<>();
                    report.put(Reporter.HOST, host);
                    report.put(Reporter.ICMP, icpmPinger.getLastResult().message);
                    report.put(Reporter.HTTP, httpPinger.getLastResult().message);
                    report.put(Reporter.TRACEROUTE, traceroutePinger.getLastResult().message);
                    reporter.report(report, last.date);
                }
            }
        }).start();
//        new Thread(()-> {
            icpmPinger.ping(resultQueue);
            httpPinger.ping(resultQueue);
            traceroutePinger.ping(resultQueue);

//        });

    }

    private PingJob(String host, Pinger icpmPinger, Pinger httpPinger, Pinger traceroutePinger, Reporter reporter) {
        this.host = host;
        this.icpmPinger = icpmPinger;
        this.httpPinger = httpPinger;
        this.traceroutePinger = traceroutePinger;
        this.resultQueue = new ConcurrentLinkedQueue<>();
        this.reporter = reporter;
    }


    public static class Builder {
        private String host;
        private Pinger icpmPinger;
        private Pinger httpPinger;
        private Pinger traceroutePinger;
        private Reporter reporter;

        public Builder setHost(String host) {
            this.host = host;
            return this;
        }

        public Builder setIcpmPinger(Pinger icpmPinger) {
            this.icpmPinger = icpmPinger;
            return this;
        }

        public Builder setHttpPinger(Pinger httpPinger) {
            this.httpPinger = httpPinger;
            return this;
        }

        public Builder setTraceroutePinger(Pinger traceroutePinger) {
            this.traceroutePinger = traceroutePinger;
            return this;
        }

        public Builder setReporter(Reporter reporter) {
            this.reporter = reporter;
            return this;
        }

        public PingJob createPingJob() {
            return new PingJob(host, icpmPinger, httpPinger, traceroutePinger, reporter);
        }
    }
}
