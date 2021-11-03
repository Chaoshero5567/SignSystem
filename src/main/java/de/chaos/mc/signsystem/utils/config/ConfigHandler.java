package de.chaos.mc.signsystem.utils.config;

import de.chaos.mc.signsystem.utils.config.sqlconfigs.SQLConfig;
import de.chaos.mc.signsystem.utils.config.sqlconfigs.SQLConfigHandler;

import java.io.File;

public class ConfigHandler {
    private SQLConfigHandler sqlConfig;
    public ConfigHandler (SQLConfigHandler sqlConfig) {
        this.sqlConfig = sqlConfig;
        loadConfigs();
    }

    public SQLConfig getSQLconfig = null;

    private void loadConfigs() {
        File path = new File("plugins/SignSystem");
        if (!path.exists()) {
            path.mkdir();
        }

        SQLConfig sqlConfigFile = sqlConfig.readSQLConfig();
        getSQLconfig = sqlConfigFile;
    }

}
