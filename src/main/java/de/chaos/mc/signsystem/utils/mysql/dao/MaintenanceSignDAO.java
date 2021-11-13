package de.chaos.mc.signsystem.utils.mysql.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Getter
@Setter
@DatabaseTable(tableName = "MaintenanceSignLines")
public class MaintenanceSignDAO {
        public static final String ID_FIELD = "ID";
        public static final String LINE_1_FIELD = "Line 1";
        public static final String LINE_2_FIELD = "Line 2";
        public static final String LINE_3_FIELD = "Line 3";
        public static final String LINE_4_FIELD = "Line 4";

        @DatabaseField(generatedId = true, columnName = ID_FIELD)
        public int ID;

        @DatabaseField(columnName = LINE_1_FIELD)
        public String Line1;

        @DatabaseField(columnName = LINE_2_FIELD)
        public String Line2;

        @DatabaseField(columnName = LINE_3_FIELD)
        public String Line3;

        @DatabaseField(columnName = LINE_4_FIELD)
        public String Line4;
}
