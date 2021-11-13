package de.chaos.mc.signsystem.utils.mysql.signs;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.signsystem.utils.mysql.dao.DAOManager;
import de.chaos.mc.signsystem.utils.mysql.dao.NormalSignDAO;

import java.sql.SQLException;

// TODO NullPointer fixen
public class NormalSignLineMemoryRepository implements NormalSignInterface {
    JdbcPooledConnectionSource source;
    public DAOManager<NormalSignDAO, Integer> daoManager;
    Integer topId = 1;
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
    }

    @Override
    public SignLinesObject getNextSign() {
        try {
            if (topId.equals(daoManager.getDAO().queryForAll().size())) {
                topId = 1;
            } else {
                topId++;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        try {
            NormalSignDAO normalSignDAO = daoManager.getDAO().queryForId(topId);
            SignLinesObject signLinesObject = SignLinesObject.builder()
                    .line0(normalSignDAO.getLine1())
                    .line1(normalSignDAO.getLine2())
                    .line2(normalSignDAO.getLine3())
                    .line3(normalSignDAO.getLine4())
                    .build();
            return signLinesObject;
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
            return SignLinesObject.builder()
                    .line0(DefaultNormalSign.Line1)
                    .line1(DefaultNormalSign.Line2)
                    .line2(DefaultNormalSign.Line3)
                    .line3(DefaultNormalSign.Line4)
                    .build();
    }

}
