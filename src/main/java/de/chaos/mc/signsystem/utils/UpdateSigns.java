package de.chaos.mc.signsystem.utils;

import de.chaos.mc.signsystem.SignSystem;
import de.chaos.mc.signsystem.utils.mysql.signs.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

public class UpdateSigns {
    private NormalSignInterface signInterface;
    private MaintenanceSignInterface maintenanceSignInterface;
    public UpdateSigns( Plugin plugin) {
        this.signInterface = SignSystem.getNormalSignInterface();
        this.maintenanceSignInterface = SignSystem.getMaintenanceSignInterface();
        startUpdater(plugin);
    }

    public void startUpdater(Plugin plugin) {
        BukkitTask task;
        task = new BukkitRunnable() {
            @Override
            public void run() {
                for (int id : SignMemoryRepository.getAllSigns.keySet()) {
                    SignObject signObject = SignMemoryRepository.getAllSigns.get(id);
                    Location location = new Location(Bukkit.getServer().getWorld(signObject.getWorld()), signObject.getX(), signObject.getY(), signObject.getZ());
                    if (location.getBlock().getState() instanceof Sign) {
                        Sign sign = (Sign) location.getBlock().getState();
                        SignLinesObject signLinesObject = signInterface.getNextSign();
                        if (!signObject.isMaintenance()) {
                            sign.setLine(0, Replacer.replace(signLinesObject.getLine0(), signObject));
                            sign.setLine(1, Replacer.replace(signLinesObject.getLine1(), signObject));
                            sign.setLine(2, Replacer.replace(signLinesObject.getLine2(), signObject));
                            sign.setLine(3, Replacer.replace(signLinesObject.getLine3(), signObject));
                            sign.update();
                        }
                        if (signObject.isMaintenance()) {
                            sign.setLine(0, Replacer.replace(signLinesObject.getLine0(), signObject));
                            sign.setLine(1, Replacer.replace(signLinesObject.getLine1(), signObject));
                            sign.setLine(2, Replacer.replace(signLinesObject.getLine2(), signObject));
                            sign.setLine(3, Replacer.replace(signLinesObject.getLine3(), signObject));
                            sign.update();
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 20*1);
    }
}
