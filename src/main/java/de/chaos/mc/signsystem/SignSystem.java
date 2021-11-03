package de.chaos.mc.signsystem;

import de.chaos.mc.signsystem.utils.config.ConfigHandler;
import de.chaos.mc.signsystem.utils.config.sqlconfigs.SQLConfigHandler;
import de.chaos.mc.signsystem.utils.mysql.MySQL;
import org.bukkit.plugin.java.JavaPlugin;

public final class SignSystem extends JavaPlugin {
    private SQLConfigHandler sqlConfigHandler;
    private ConfigHandler configHandler;
    private MySQL mySQL;


    @Override
    public void onEnable() {
        init();
    }

    public void init() {
        initVariables();
        mySQL.connectSQL();
        mySQL.update("CREATE TABLE IF NOT EXISTS Signs (WORLD VARCHAR(100), X INT(100), Y INT(100), Z INT(100), Server VARCHAR(100))");
    }

    public void initVariables() {
        sqlConfigHandler = new SQLConfigHandler();
        configHandler = new ConfigHandler(sqlConfigHandler);
        mySQL = new MySQL(configHandler);
    }

    @Override
    public void onDisable() {
        mySQL.disconnectSQL();
    }
}
