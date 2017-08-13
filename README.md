# ping-test
The application calls ping command, traceroute command from cli and sends http GET to predefined host.
If one of the commands fails, 
the last results of commands are stored in log file and a POST http with the smae info is send to another host.

Usage:
Edit config.json, then 
```bash
$ mvn clean package
$ cd target 
$ java -jar ping-1.0-SNAPSHOT-jar-with-dependencies.jar 
