import common.Config;

import java.nio.file.Paths;

import static org.junit.Assert.assertEquals;

public class ConfigTest {

    @org.junit.Test
    public void testGetConfig() throws Exception {
        String file = getClass().getResource("test_config.json").getFile();
        Config config = Config.getConfig(Paths.get(file));
        assertEquals(config.getHttp().getDelay(), 6);
        assertEquals(config.getIcmp().getDelay(), 5);
    }


}