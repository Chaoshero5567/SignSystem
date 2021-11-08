package de.chaos.mc.signsystem.utils.mysql.signs;

import org.bukkit.Location;

public interface SignInterface {
    public SignObject setSign(SignObject signObject);
    public SignObject getSign(int id);
    public SignObject getSign(Location location);
}
