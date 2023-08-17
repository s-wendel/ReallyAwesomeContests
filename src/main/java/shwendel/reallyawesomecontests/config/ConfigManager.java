package shwendel.reallyawesomecontests.config;

import java.util.HashMap;
import java.util.Map;

public class ConfigManager {

    private static Map<ConfigType, Config> configs = new HashMap<>();

    static {
        for(ConfigType configType : ConfigType.values()) {
            configs.put(configType, new Config(configType));
        }
    }

    public static Map<ConfigType, Config> getConfigs() {
        return configs;
    }

}
