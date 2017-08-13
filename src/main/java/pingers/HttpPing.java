package pingers;

import common.Config;
import common.Result;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class HttpPing extends PingerImpl implements Pinger {

    private int timeout;
    public HttpPing(String host, Config.Http config) {
        super(host, null, config.getDelay());
        this.timeout = config.getTimeout();
    }

    @Override
    protected Result innerPing() {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://" + host).openConnection();
            System.out.println("started tcp http://" + host);
            connection.setConnectTimeout(timeout);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            connection.connect();
            String processOut, resultMessage = "";
            while ((processOut = reader.readLine()) != null) {
                resultMessage += processOut;
            }
            System.out.println(resultMessage);
            return new Result(resultMessage, connection.getResponseCode() < 400, new Date());
        } catch (IOException e) {
            return new Result(e.getMessage(), false, new Date());
        }
    }
}
