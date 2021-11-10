package de.chaos.mc.signsystem.listeners;

import de.chaos.mc.signsystem.SignSystem;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (event.getBlock().getState() instanceof Sign) {
            if (SignSystem.getSignInterface().getSign(event.getBlock().getLocation()) != null) {
                event.setCancelled(true);
            } else return;
        }
    }
}
