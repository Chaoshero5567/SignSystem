package de.chaos.mc.signsystem.utils.mysql.signs;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.signsystem.utils.mysql.dao.DAOManager;
import de.chaos.mc.signsystem.utils.mysql.dao.SignDAO;
import org.bukkit.Location;

import java.sql.SQLException;

//TODO mach das FERTIG DU IDIOT
public class SignMemoryRepository implements SignInterface {
    public JdbcPooledConnectionSource connectionSource;
    public DAOManager daoManager;

    public SignMemoryRepository(JdbcPooledConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
        daoManager = new DAOManager<>(SignDAO.class, connectionSource);
    }

    @Override
    public SignObject setSign(SignObject signObject) {
        try {
            daoManager.getDAO().queryBuilder().prepare();
            daoManager.getDAO().queryBuilder().selectColumns(SignDAO.WORLD_FIELD, SignDAO.X_FIELD, SignDAO.Y_FIELD, SignDAO.Z_FIELD, SignDAO.SERVER_FIELD, SignDAO.MAINTENANCE_FIELD);
            daoManager.getDAO().queryBuilder().query();
            daoManager.getDAO().create(signObject);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return signObject;
    }

    @Override
    public SignObject getSign(int id) {
        return null;
    }

    @Override
    public SignObject getSign(Location location) {
        return null;
    }
}