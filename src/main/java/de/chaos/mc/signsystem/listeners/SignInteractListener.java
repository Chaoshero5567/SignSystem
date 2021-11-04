package de.chaos.mc.signsystem.listeners;

import de.chaos.mc.signsystem.utils.BungeeServerSender;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignInteractListener implements Listener {
    @EventHandler
    public static void onBlockInetract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getClickedBlock() instanceof Sign) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (sign.getLine(2).equalsIgnoreCase("maintenance")) {
                    event.getPlayer().sendMessage("Server is in maintenance");
                } else {
                    String server = sign.getLine(1);
                    BungeeServerSender.sendPlayerToServer(event.getPlayer(), server);
                }
            }
        }
    }
}
