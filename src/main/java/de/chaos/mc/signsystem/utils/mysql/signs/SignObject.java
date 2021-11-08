package de.chaos.mc.signsystem.utils.mysql.signs;

import com.j256.ormlite.table.DatabaseTable;
import de.chaos.mc.signsystem.utils.mysql.dao.SignDAO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.bukkit.Location;

@Getter
@AllArgsConstructor
@Data
@Builder
@DatabaseTable(daoClass = SignDAO.class)
public class SignObject {
    public Long id;
    public Location location;
    public String Server;
    public boolean maintenance;
}
