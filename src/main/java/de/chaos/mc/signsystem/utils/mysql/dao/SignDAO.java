package de.chaos.mc.signsystem.utils.mysql.dao;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Location;

@AllArgsConstructor
@NoArgsConstructor
@Data
@DatabaseTable(tableName = "signs")
public class SignDAO {
    public static final String ID_FIELD = "ID";
    public static final String LOCATION_FIELD = "LOCATION";
    public static final String SERVER_FIELD = "SERVER";
    public static final String MAINTENANCE_FIELD = "MAINTENANCE";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int id;

    @DatabaseField(columnName = LOCATION_FIELD)
    public Location location;

    @DatabaseField(columnName = SERVER_FIELD)
    public String Server;

    @DatabaseField(columnName = MAINTENANCE_FIELD)
    public boolean maintenance;
}
