package de.chaos.mc.signsystem.utils.config.sqlconfigs;

import com.google.gson.Gson;
import de.chaos.mc.signsystem.utils.mysql.DefaultSQLValues;
import lombok.Getter;

import java.io.*;

@Getter
public class SQLConfigHandler {

    private Gson gson = new Gson();
    private final File sqlFile = new File("plugins//SignSystem//SQL.json");

    public void saveSQLConfig(String host, Long port, String user, String pw, String database) {
        SQLConfig sqlConfig = new SQLConfig(host, port, user, pw, database);



        try (FileWriter fileWriter = new FileWriter(sqlFile)) {
            if (!sqlFile.exists()) sqlFile.createNewFile();
            fileWriter.write(gson.toJson(sqlConfig));
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public SQLConfig readSQLConfig() {
        if (!sqlFile.exists()) {
            saveSQLConfig(DefaultSQLValues.host, DefaultSQLValues.port, DefaultSQLValues.user, DefaultSQLValues.pw, DefaultSQLValues.database);
        }

        try {
            SQLConfig sqlConfig = gson.fromJson(new FileReader(sqlFile), SQLConfig.class);
            return sqlConfig;
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}