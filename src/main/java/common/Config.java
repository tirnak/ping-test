package common;

import com.google.gson.Gson;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class Config {
    private String[] hosts;
    private String output_file;
    private Icmp icmp;
    private Http http;
    private Tracert tracert;
    private Report report;

    public class Icmp {
        private int delay;
        private String options;

        public int getDelay() { return delay; }
        public String getOptions() { return options; }
    }
    public class Http {
        private int delay;
        private int timeout;

        public int getDelay() { return delay; }
        public int getTimeout() { return timeout; }
    }
    public class Tracert {
        private int delay;
        private String options;

        public int getDelay() { return delay; }
        public String getOptions() { return options; }
    }
    public class Report {
        private String url;
        public String getUrl() { return url; }
    }

    public static Config getConfig(Path pathToFile) throws IOException {
        String configJson = String.join("", Files.readAllLines(pathToFile));
        Gson gson = new Gson();
        return gson.fromJson(configJson, Config.class);
    }

    public static Config getConfig(InputStream inputStream) throws IOException {
        String configJson = "";
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                configJson += line;
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        return gson.fromJson(configJson, Config.class);
    }

    public String[] getHosts() { return hosts; }
    public String getOutputFileName() { return output_file; }
    public Icmp getIcmp() { return icmp; }
    public Http getHttp() { return http; }
    public Tracert getTracert() { return tracert; }
    public Report getReport() { return report; }
}
