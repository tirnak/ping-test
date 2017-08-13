package common;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

public class Reporter {

    public final static String HOST = "host", ICMP = "icmp_ping", HTTP = "tcp_ping", TRACEROUTE = "trace",
        POST_METHOD = "POST",
        CONTENT_TYPE = "Content-Type", JSON_UTF8 = "application/json; charset=UTF-8",
        UTF8 = "UTF-8";
    private Gson gson = new Gson();
    private Path pathToLogFile;
    private String urlToSendReports;

    public void report(Map<String, String> message, Date time) {
        try {
            String jsonedMessage = gson.toJson(message);
            String jsonedMessageAndTime = String.format("[WARN] %s %s", time.toString(), jsonedMessage);
            if (!Files.exists(pathToLogFile)) Files.createFile(pathToLogFile);
            Files.write(pathToLogFile, Collections.singleton(jsonedMessageAndTime), StandardOpenOption.APPEND);

            HttpURLConnection connection = (HttpURLConnection) new URL("http://"+urlToSendReports).openConnection();
            connection.setRequestMethod(POST_METHOD);
            connection.setRequestProperty(CONTENT_TYPE, JSON_UTF8 );
            OutputStream outStream = connection.getOutputStream();
            outStream.write(jsonedMessage.getBytes(UTF8));
            connection.disconnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Reporter(String urlToSendReports, Path pathToLogFile) {
        this.urlToSendReports = urlToSendReports;
        this.pathToLogFile = pathToLogFile;
    }
}
