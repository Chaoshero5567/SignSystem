package de.chaos.mc.signsystem.utils.mysql.signs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
@Builder
public class SignObject {
    public int id;
    public String world;
    public int X;
    public int Y;
    public int Z;
    public String Server;
    public boolean maintenance;
}
