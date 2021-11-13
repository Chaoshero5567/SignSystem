package de.chaos.mc.signsystem.utils;

import de.chaos.mc.signsystem.utils.mysql.signs.SignObject;

public class Replacer {
    public static String replace(String string, SignObject signObject) {
        String replacedString = string;
        replacedString.replace("&", "§")
                .replace("*server_name*", signObject.getServer());
        return replacedString;
    }
}
