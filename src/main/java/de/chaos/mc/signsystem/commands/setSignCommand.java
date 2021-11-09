package de.chaos.mc.signsystem.commands;

import de.chaos.mc.signsystem.utils.mysql.dao.SignDAO;
import de.chaos.mc.signsystem.utils.mysql.signs.SignInterface;
import de.chaos.mc.signsystem.utils.mysql.signs.SignObject;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class setSignCommand implements CommandExecutor {
    private final SignInterface signInterface;

    public setSignCommand(SignInterface SignInterface) {
        this.signInterface = SignInterface;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Block block = player.getTargetBlock(null, 5);
            Location blockLocation = block.getLocation();
            if (block.getState() instanceof Sign) {
                String arg0 = args[0];
                Boolean arg1 = Boolean.valueOf(args[1]);
                if (args.length != 2) {
                    player.sendMessage("/setSign: SERVER MAINTENANCE");
                    return false;
                } else {
                    if (arg0 == null) {
                        player.sendMessage("/setSign: SERVER MAINTENANCE");
                        return false;
                    } else {
                        if (arg1 == null) {
                            player.sendMessage("/setSign: SERVER MAINTENANCE");
                            return false;
                        } else {
                            SignObject signObject = SignObject.builder()
                                    .world(blockLocation.getWorld().getName())
                                    .X(block.getX())
                                    .Z(block.getZ())
                                    .Y(block.getY())
                                    .Server(arg0)
                                    .maintenance(arg1)
                                    .build();
                            signInterface.setSign(signObject);
                            player.sendMessage("Sign was created");
                            return true;
                        }
                    }
                }
            } else {
                player.sendMessage("There is no sign");
                return false;
            }
        } else {
            return false;
       }
    }
}