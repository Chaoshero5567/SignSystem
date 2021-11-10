package de.chaos.mc.signsystem.utils.mysql.signs;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.signsystem.utils.mysql.dao.DAOManager;
import de.chaos.mc.signsystem.utils.mysql.dao.MaintenanceSignDAO;

import java.sql.SQLException;
import java.util.HashMap;

public class MaintenanceSignLineMemoryRepository implements MaintenanceSignInterface {
    JdbcPooledConnectionSource source;
    public DAOManager<MaintenanceSignDAO, Integer> daoManager;
    public static HashMap<Integer, MaintenanceSignDAO> getAllSigns = new HashMap<>();
    int topId = 0;
    public MaintenanceSignLineMemoryRepository(JdbcPooledConnectionSource connectionSource) {
        this.source = connectionSource;
        this.daoManager = new DAOManager<MaintenanceSignDAO, Integer>(MaintenanceSignDAO.class, source);
        try {
            if (!daoManager.getDAO().isTableExists()) {
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
        getAllLines();
    }

    @Override
    public String getNextLine(int line) {
        String string = null;
        if (getAllSigns.size() == topId) {
            topId = 0;
            topId++;
            if (line == 1) {
                string = getAllSigns.get(topId).getLine1();
            }
            if (line == 2) {
                string = getAllSigns.get(topId).getLine2();
            }
            if (line == 3) {
                string = getAllSigns.get(topId).getLine3();
            }
            if (line == 4) {
                string = getAllSigns.get(topId).getLine4();
            }
        } else {
            topId++;
            if (line == 1) {
                string = getAllSigns.get(topId).getLine1();
            }
            if (line == 2) {
                string = getAllSigns.get(topId).getLine2();
            }
            if (line == 3) {
                string = getAllSigns.get(topId).getLine3();
            }
            if (line == 4) {
                string = getAllSigns.get(topId).getLine4();
            }
        }
        return string;
    }

    public void getAllLines() {
        int id = 0;
        try {
            for (MaintenanceSignDAO maintenanceSignDAO : daoManager.getDAO().queryForAll()) {
                getAllSigns.put(maintenanceSignDAO.getID(), maintenanceSignDAO);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
