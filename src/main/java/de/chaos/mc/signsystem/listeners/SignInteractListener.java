package de.chaos.mc.signsystem.listeners;

import de.chaos.mc.signsystem.utils.BungeeServerSender;
import de.chaos.mc.signsystem.utils.mysql.signs.SignInterface;
import de.chaos.mc.signsystem.utils.mysql.signs.SignObject;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class SignInteractListener implements Listener {
    private SignInterface signInterface;
    public SignInteractListener(SignInterface signInterface) {
        this.signInterface = signInterface;
    }
    @EventHandler
    public void onBlockInetract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock().getState() instanceof Sign) {
                Block block = event.getClickedBlock();
                int id = 0;
                SignObject signObject = signInterface.getSign(block.getLocation());
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
        }
    }
}