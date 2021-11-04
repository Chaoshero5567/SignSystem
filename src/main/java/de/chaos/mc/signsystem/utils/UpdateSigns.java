package de.chaos.mc.signsystem.utils;

import de.chaos.mc.signsystem.utils.mysql.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class UpdateSigns {
    MySQL mySQL;
    public HashMap<Long, SignObject> signs = new HashMap<>();
    public UpdateSigns(MySQL mySQL, Plugin plugin) {
        this.mySQL = mySQL;
        this.signs = mySQL.getSign;
        startUpdater(plugin);
    }

    public void startUpdater(Plugin plugin) {
        BukkitTask task;
        task = new BukkitRunnable() {
            @Override
            public void run() {
                for (Long id : signs.keySet()) {
                    SignObject signObject = signs.get(id);
                    World world = Bukkit.getWorld(signObject.getWorld());
                    Block block = world.getBlockAt(signObject.x, signObject.y, signObject.z);
                    Sign sign = (Sign) block.getState();
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
