package de.chaos.mc.signsystem.listeners;

import de.chaos.mc.signsystem.utils.BungeeServerSender;
import de.chaos.mc.signsystem.utils.SignObject;
import de.chaos.mc.signsystem.utils.mysql.MySQL;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignInteractListener implements Listener {
    private static MySQL mySQL;
    public SignInteractListener(MySQL mySQL) {
        this.mySQL = mySQL;
    }
    @EventHandler
    public static void onBlockInetract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            System.out.println("check 1");
            if (event.getClickedBlock().getState() instanceof Sign) {
                System.out.println("check 2");
                Block block = event.getClickedBlock();
                Long id = Long.valueOf(0);
                do {
                    System.out.println("check 3");
                    id = Math.addExact(id, 1);
                    SignObject signObject = mySQL.getSign.get(id);
                    if (signObject.getX() == block.getX() && signObject.getY() == block.getY() && signObject.getZ() == block.getZ()) {
                        if (signObject.maintenance) {
                            event.getPlayer().sendMessage("Server is in maintenance");
                            System.out.println("check 4");
                            return;
                        } else {
                            BungeeServerSender.sendPlayerToServer(event.getPlayer(), signObject.getServer());
                            System.out.println("check 4");
                            return;
                        }
                    }
                } while (id != mySQL.getSign.size());
            }
        }
    }
}