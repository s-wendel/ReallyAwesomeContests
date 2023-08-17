package shwendel.reallyawesomecontests.config;

import shwendel.reallyawesomecontests.ReallyAwesomeContests;

import java.io.File;

public enum ConfigType {

    CONFIG("config.yml", ""),
    MESSAGES(ReallyAwesomeContests.getInstance().getConfig().getString("options.messages"), File.separator + "lang" + File.separator),

    ;

    private String fileName;
    private String path;

    ConfigType(String fileName, String path) {
        this.fileName = fileName;
        this.path = path;
    }

    public String getFileName() {
        return fileName;
    }

    public String getPath()  {
        return path;
    }

}
