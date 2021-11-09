package de.chaos.mc.signsystem.utils;

import de.chaos.mc.signsystem.utils.mysql.signs.SignObject;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class UpdateSigns {
    public HashMap<Long, SignObject> signs = new HashMap<>();
    public UpdateSigns( Plugin plugin) {
        startUpdater(plugin);
    }

    public void startUpdater(Plugin plugin) {
        BukkitTask task;
        task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Long id : signs.keySet()) {
                    SignObject signObject = signs.get(id);
                    Location location = new Location(Bukkit.getServer().getWorld(signObject.getWorld()), signObject.getX(), signObject.getY(), signObject.getZ());
                    Sign sign = (Sign) location.getBlock().getState();
                    sign.setLine(0, "---------------");
                    sign.setLine(1, signObject.getServer());
                    if (signObject.maintenance) {
                        sign.setLine(2, "Maintenance");
                    } else {
                        sign.setLine(2, "Online");
                    }
                    sign.setLine(3, "---------------");
                    sign.update(true);
                }
            }
        }.runTaskTimer(plugin, 5, 1);
    }
}
