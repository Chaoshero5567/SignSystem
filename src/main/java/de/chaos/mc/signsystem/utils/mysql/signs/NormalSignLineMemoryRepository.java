package de.chaos.mc.signsystem.utils.mysql.signs;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.signsystem.utils.mysql.dao.DAOManager;
import de.chaos.mc.signsystem.utils.mysql.dao.NormalSignDAO;

import java.sql.SQLException;
import java.util.HashMap;

public class NormalSignLineMemoryRepository implements NormalSignInterface {
    JdbcPooledConnectionSource source;
    public DAOManager<NormalSignDAO, Integer> daoManager;
    public static HashMap<Integer, NormalSignDAO> getAllSigns = new HashMap<>();
    int topId = 0;
    public NormalSignLineMemoryRepository(JdbcPooledConnectionSource connectionSource) {
        this.source = connectionSource;
        this.daoManager = new DAOManager<NormalSignDAO, Integer>(NormalSignDAO.class, source);
        try {
            if (daoManager.getDAO().queryForId(1) == null) {
                NormalSignDAO normalSignDAO = new NormalSignDAO();
                normalSignDAO.Line1 = DefaultNormalSign.Line1;
                normalSignDAO.Line2 = DefaultNormalSign.Line2;
                normalSignDAO.Line3 = DefaultNormalSign.Line3;
                normalSignDAO.Line4 = DefaultNormalSign.Line4;
                daoManager.getDAO().createOrUpdate(normalSignDAO);
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
            for (NormalSignDAO normalSignDAO : daoManager.getDAO().queryForAll()) {
                getAllSigns.put(normalSignDAO.getID(), normalSignDAO);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}
