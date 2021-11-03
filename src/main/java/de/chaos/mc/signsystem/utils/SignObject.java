package de.chaos.mc.signsystem.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Data
@Builder
public class SignObject {
    public String world;
    public Integer x;
    public Integer y;
    public Integer z;
    public String Server;
    public boolean maintenance;
}
