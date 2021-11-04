package de.chaos.mc.signsystem;

import de.chaos.mc.signsystem.commands.setSignCommand;
import de.chaos.mc.signsystem.listeners.SignInteractListener;
import de.chaos.mc.signsystem.utils.UpdateSigns;
import de.chaos.mc.signsystem.utils.config.ConfigHandler;
import de.chaos.mc.signsystem.utils.config.sqlconfigs.SQLConfigHandler;
import de.chaos.mc.signsystem.utils.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SignSystem extends JavaPlugin {
    private static SignSystem instance;
    private SQLConfigHandler sqlConfigHandler;
    private ConfigHandler configHandler;
    private MySQL mySQL;
    private UpdateSigns updateSigns;


    @Override
    public void onEnable() {

        init(Bukkit.getPluginManager());
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    }

    public void init(PluginManager pluginManager) {
        initVariables();
        mySQL.connectSQL();
        mySQL.update("CREATE TABLE IF NOT EXISTS Signs (ID INT(100) PRIMARY KEY AUTO_INCREMENT, WORLD VARCHAR(100), X INT(100), Y INT(100), Z INT(100), SERVER VARCHAR(100), MAINTENANCE VARCHAR(100))");
        mySQL.getSigns();
        initListeners(pluginManager);
        initCommands();
    }

    public void initListeners(PluginManager pluginManager) {
        pluginManager.registerEvents(new SignInteractListener(), this);
    }

    public void initCommands() {
        getCommand("setsign").setExecutor(new setSignCommand(mySQL));
    }

    public void initVariables() {
        instance = this;
        sqlConfigHandler = new SQLConfigHandler();
        configHandler = new ConfigHandler(sqlConfigHandler);
        mySQL = new MySQL(configHandler);
        updateSigns = new UpdateSigns(mySQL, this);
    }

    @Override
    public void onDisable() {
        mySQL.disconnectSQL();
    }

    public static SignSystem getInstance() {
        return instance;
    }
}