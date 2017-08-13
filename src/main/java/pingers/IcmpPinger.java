package pingers;

import common.Config;
import common.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class IcmpPinger extends PingerImpl implements Pinger {

    private static final String PING_COMMAND = "ping";
    private List<String> command = new ArrayList<>();

    public IcmpPinger (String host, Config.Icmp config) {
        super(host, config.getOptions(), config.getDelay());
        command.add(PING_COMMAND);
        String[] optionsArray = options.split(SPACE);
        if (optionsArray.length > 1) {
            Collections.addAll(command, options.split(SPACE));
        }
        command.add(host);
    }

    @Override
    protected Result innerPing() {
        ProcessBuilder pb = new ProcessBuilder(command);
        try {
            Process process = pb.start();
            System.out.println("started ping " + host + " at " + new Date().toString());
            BufferedReader stdout = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String processOut, resultMessage = "";
            while ((processOut = stdout.readLine()) != null) {
                resultMessage += processOut;
            }
            BufferedReader stderr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((processOut = stderr.readLine()) != null) {
                resultMessage += processOut;
            }
            process.waitFor();
            System.out.println(resultMessage);
            return new Result(resultMessage, process.exitValue() == 0, new Date());

        } catch (InterruptedException | IOException e) {
            return new Result(e.getMessage(), false, new Date());
            //TODO improve catch handling
        }
    }
}
