package de.chaos.mc.signsystem.utils.mysql.dao;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@DatabaseTable(tableName = "Signs")
public class SignDAO {
    public static final String ID_FIELD = "ID";
    public static final String WORLD_FIELD = "WORLD";
    public static final String X_FIELD = "X";
    public static final String Y_FIELD = "Y";
    public static final String Z_FIELD = "Z";
    public static final String SERVER_FIELD = "SERVER";
    public static final String MAINTENANCE_FIELD = "MAINTENANCE";

    @DatabaseField(generatedId = true, columnName = ID_FIELD)
    public int id;

    @DatabaseField(columnName = WORLD_FIELD)
    public String world;

    @DatabaseField(columnName = X_FIELD)
    public int X;

    @DatabaseField(columnName = Y_FIELD)
    public int Y;

    @DatabaseField(columnName = Z_FIELD)
    public int Z;

    @DatabaseField(columnName = SERVER_FIELD)
    public String Server;

    @DatabaseField(columnName = MAINTENANCE_FIELD)
    public String maintenance;
}
