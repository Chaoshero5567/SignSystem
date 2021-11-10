package de.chaos.mc.signsystem.commands;

import de.chaos.mc.signsystem.SignSystem;
import de.chaos.mc.signsystem.utils.Permissions;
import de.chaos.mc.signsystem.utils.mysql.signs.SignObject;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class deleteSignCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player = (Player) sender;
        if (player.hasPermission(Permissions.delSign)) {
            Block block = player.getTargetBlock(null, 5);
            Location location = block.getLocation();
            if (SignSystem.getSignInterface().getSign(location) != null) {
                SignObject signObject = SignSystem.getSignInterface().getSign(location);
                SignSystem.getSignInterface().delSign(signObject.getId());
            } else {
                player.sendMessage("There is no Sign");
            }

        }

        return false;
    }
}
