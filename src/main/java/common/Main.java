package common;

import pingers.HttpPing;
import pingers.IcmpPinger;
import pingers.TracertPing;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        new Main().run();
        Thread.sleep(100000);
    }

    private void run() throws IOException {
        Config config = Config.getConfig(getClass().getClassLoader().getResourceAsStream("config.json"));

        for (String host : config.getHosts()) {
            PingJob job = new PingJob.Builder().setHost(host)
                    .setHttpPinger(new HttpPing(host, config.getHttp()))
                    .setIcpmPinger(new IcmpPinger(host, config.getIcmp()))
                    .setTraceroutePinger(new TracertPing(host, config.getTracert()))
                    .setHost(host).setReporter(new Reporter(config.getReport().getUrl(), Paths.get(config.getOutputFileName()))).createPingJob();
            job.run();
        }
    }

}
