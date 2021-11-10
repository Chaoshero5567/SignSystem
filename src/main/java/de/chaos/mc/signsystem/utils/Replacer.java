package de.chaos.mc.signsystem.utils;

import de.chaos.mc.signsystem.utils.mysql.signs.SignObject;

public class Replacer {
    public static String replace(String string, SignObject signObject) {
        string.replace("&", "§");
        return string;
    }
}
