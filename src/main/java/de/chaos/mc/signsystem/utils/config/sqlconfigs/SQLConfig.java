package de.chaos.mc.signsystem.utils.config.sqlconfigs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class SQLConfig {
    private String host;
    private Long port;
    private String user;
    private String password;
    private String database;
}
