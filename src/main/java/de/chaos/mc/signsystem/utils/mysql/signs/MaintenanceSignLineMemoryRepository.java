package de.chaos.mc.signsystem.utils.mysql.signs;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.signsystem.utils.mysql.dao.DAOManager;
import de.chaos.mc.signsystem.utils.mysql.dao.MaintenanceSignDAO;

import java.sql.SQLException;

// TODO NullPointer fixen
public class MaintenanceSignLineMemoryRepository implements MaintenanceSignInterface {
    JdbcPooledConnectionSource source;
    public DAOManager<MaintenanceSignDAO, Integer> daoManager;
    Integer topId = 0;
    public MaintenanceSignLineMemoryRepository(JdbcPooledConnectionSource connectionSource) {
        this.source = connectionSource;
        this.daoManager = new DAOManager<MaintenanceSignDAO, Integer>(MaintenanceSignDAO.class, source);
        try {
            if (daoManager.getDAO().queryForId(1) == null) {
                MaintenanceSignDAO maintenanceSignDAO = new MaintenanceSignDAO();
                maintenanceSignDAO.Line1 = MaintenanceNormalSign.Line1;
                maintenanceSignDAO.Line2 = MaintenanceNormalSign.Line2;
                maintenanceSignDAO.Line3 = MaintenanceNormalSign.Line3;
                maintenanceSignDAO.Line4 = MaintenanceNormalSign.Line4;
                daoManager.getDAO().createOrUpdate(maintenanceSignDAO);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public SignLinesObject getNextSign() {
        try {
            if (topId == daoManager.getDAO().queryForAll().size()) {
                topId = 1;
            } else {
                topId++;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            MaintenanceSignDAO signDAO = daoManager.getDAO().queryForId(topId);
            SignLinesObject signLinesObject = SignLinesObject.builder()
                    .line0(signDAO.getLine1())
                    .line1(signDAO.getLine2())
                    .line2(signDAO.getLine3())
                    .line3(signDAO.getLine4())
                    .build();
            return signLinesObject;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        return SignLinesObject.builder()
                .line0(MaintenanceNormalSign.Line1)
                .line1(MaintenanceNormalSign.Line2)
                .line2(MaintenanceNormalSign.Line3)
                .line3(MaintenanceNormalSign.Line4)
                .build();
    }
}
