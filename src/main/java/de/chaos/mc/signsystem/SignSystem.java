package de.chaos.mc.signsystem;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import com.j256.ormlite.support.BaseConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import de.chaos.mc.signsystem.commands.deleteSignCommand;
import de.chaos.mc.signsystem.commands.setSignCommand;
import de.chaos.mc.signsystem.listeners.SignInteractListener;
import de.chaos.mc.signsystem.utils.BungeeServerSender;
import de.chaos.mc.signsystem.utils.UpdateSigns;
import de.chaos.mc.signsystem.utils.config.ConfigHandler;
import de.chaos.mc.signsystem.utils.config.sqlconfigs.SQLConfig;
import de.chaos.mc.signsystem.utils.config.sqlconfigs.SQLConfigHandler;
import de.chaos.mc.signsystem.utils.mysql.signs.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class SignSystem extends JavaPlugin {
    private static SignSystem instance;
    private SQLConfigHandler sqlConfigHandler;
    private ConfigHandler configHandler;
    private UpdateSigns updateSigns;
    private BungeeServerSender bungeeServerSender;
    public JdbcPooledConnectionSource connectionSource;
    public SignMemoryRepository signMemoryRepository;
    public static SignInterface signInterface;
    public NormalSignLineMemoryRepository normalSignLineMemoryRepository;
    public static NormalSignInterface normalSignInterface;
    public MaintenanceSignLineMemoryRepository maintenanceSignLineMemoryRepository;
    public static MaintenanceSignInterface maintenanceSignInterface;

    @Override
    public void onEnable() {

        init(Bukkit.getPluginManager(), connectionSource);
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", bungeeServerSender);
    }

    public void init(PluginManager pluginManager, JdbcPooledConnectionSource source) {
        source = new JdbcPooledConnectionSource();
        sqlConfigHandler = new SQLConfigHandler();
        SQLConfig sqlConfig = sqlConfigHandler.readSQLConfig();
        source.setUrl("jdbc:mysql://" +  sqlConfig.getHost() + ":" + sqlConfig.getPort() + "/" + sqlConfig.getDatabase());
        source.setUsername(sqlConfig.getUser());
        source.setPassword(sqlConfig.getPassword());
        try {
            source.initialize();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        initVariables(source);
        initListeners(pluginManager);
        initCommands();
    }

    public void initListeners(PluginManager pluginManager) {
        pluginManager.registerEvents(new SignInteractListener(signInterface), this);
    }

    public void initCommands() {
        getCommand("setsign").setExecutor(new setSignCommand(signInterface));
        getCommand("deleteSign").setExecutor(new deleteSignCommand());
    }

    public void initVariables(JdbcPooledConnectionSource source) {
        instance = this;
        configHandler = new ConfigHandler(sqlConfigHandler);
        signMemoryRepository = new SignMemoryRepository(source);
        signInterface = signMemoryRepository;
        normalSignLineMemoryRepository = new NormalSignLineMemoryRepository(source);
        normalSignInterface = normalSignLineMemoryRepository;
        maintenanceSignLineMemoryRepository = new MaintenanceSignLineMemoryRepository(source);
        maintenanceSignInterface = maintenanceSignLineMemoryRepository;
        updateSigns = new UpdateSigns(this);
        bungeeServerSender = new BungeeServerSender();
    }

    @Override
    public void onDisable() {
    }

    public static SignSystem getInstance() {
        return instance;
    }

    public static SignInterface getSignInterface() {
        return signInterface;
    }

    public static NormalSignInterface getNormalSignInterface() {
        return normalSignInterface;
    }

    public static MaintenanceSignInterface getMaintenanceSignInterface() {
        return maintenanceSignInterface;
    }
}