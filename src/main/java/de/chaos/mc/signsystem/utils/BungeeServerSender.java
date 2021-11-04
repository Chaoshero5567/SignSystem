package de.chaos.mc.signsystem.utils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.chaos.mc.signsystem.SignSystem;
import org.bukkit.entity.Player;

public class BungeeServerSender {
    public static void sendPlayerToServer(Player player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(server);
        player.sendPluginMessage(SignSystem.getInstance(), "BungeeCord", out.toByteArray());
    }
}