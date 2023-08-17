package shwendel.reallyawesomecontests.config;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import shwendel.reallyawesomecontests.ReallyAwesomeContests;

import java.io.File;
import java.io.IOException;

public class Config {

    private File file;
    private FileConfiguration fileConfiguration;
    private ConfigType configType;

    public Config(ConfigType configType) {
        this.configType = configType;
        this.file = new File(ReallyAwesomeContests.getInstance().getDataFolder().getPath() + configType.getPath(), configType.getFileName());

        if(!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        this.fileConfiguration = new YamlConfiguration();

        try {
            fileConfiguration.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return fileConfiguration;
    }

    public ConfigType getConfigType() {
        return configType;
    }

    public void saveConfig() {
        try {
            fileConfiguration.save(file);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void reloadConfig() {
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

}
