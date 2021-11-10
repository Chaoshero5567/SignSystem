package de.chaos.mc.signsystem.utils.mysql.signs;

import com.j256.ormlite.jdbc.JdbcPooledConnectionSource;
import de.chaos.mc.signsystem.utils.mysql.dao.DAOManager;
import de.chaos.mc.signsystem.utils.mysql.dao.SignDAO;
import org.bukkit.Location;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class SignMemoryRepository implements SignInterface {
    public JdbcPooledConnectionSource connectionSource;
    public DAOManager<SignDAO, Integer> daoManager;
    public static HashMap<Integer, SignObject> getAllSigns = new HashMap<>();

    public SignMemoryRepository(JdbcPooledConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
        this.daoManager = new DAOManager<SignDAO, Integer>(SignDAO.class, connectionSource);
        loadAllSigns();
    }

    @Override
    public SignObject setSign(SignObject signObject) {
        try {
            SignDAO dao = new SignDAO();
            dao.setWorld(signObject.getWorld());
            dao.setX(signObject.getX());
            dao.setY(signObject.getY());
            dao.setZ(signObject.getZ());
            dao.setServer(signObject.getServer());
            dao.setMaintenance(String.valueOf(signObject.isMaintenance()));
            daoManager.getDAO().createOrUpdate(dao);
            int id  = getAllSigns.size();
            getAllSigns.put(++id, signObject);

        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return signObject;
    }

    @Override
    public SignObject getSign(int id) {
        SignObject signObject = null;
        try {
            SignDAO signDAO = daoManager.getDAO().queryForId(id);
            signObject = SignObject.builder()
                    .id(signDAO.getId())
                    .world(signDAO.getWorld())
                    .X(signDAO.getX())
                    .Y(signDAO.getY())
                    .Z(signDAO.getZ())
                    .Server(signDAO.getServer())
                    .maintenance(Boolean.parseBoolean(String.valueOf(signObject.isMaintenance())))
                    .build();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return signObject;
    }

    @Override
    public SignObject getSign(Location location) {
        SignObject signObject = null;
        try {
            List<SignDAO> signDAOList= daoManager.getDAO().queryBuilder().where()
                    .eq(SignDAO.WORLD_FIELD, location.getWorld().getName()).and()
                    .eq(SignDAO.X_FIELD, location.getX()).and()
                    .eq(SignDAO.Y_FIELD, location.getY()).and()
                    .eq(SignDAO.Z_FIELD, location.getZ()).query();
            SignDAO signDAO = signDAOList.get(1);
            signObject = SignObject.builder()
                    .id(signDAO.getId())
                    .world(signDAO.getWorld())
                    .X(signDAO.getX())
                    .Y(signDAO.getY())
                    .Z(signDAO.getZ())
                    .Server(signDAO.getServer())
                    .maintenance(Boolean.parseBoolean(signDAO.getMaintenance()))
                    .build();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return signObject;
    }

    @Override
    public SignObject delSign(int id) {
        SignObject signObject = null;
        try {
            signObject = getSign(id);
            daoManager.getDAO().deleteById(id);
            getAllSigns.remove(id);
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return signObject;
    }

    public void loadAllSigns() {
        try {
            int id = 0;
            for (SignDAO signDAO : daoManager.getDAO().queryForAll()) {
                SignObject signObject = SignObject.builder()
                        .world(signDAO.getWorld())
                        .X(signDAO.getX())
                        .Y(signDAO.getY())
                        .Z(signDAO.getZ())
                        .Server(signDAO.getServer())
                        .maintenance(Boolean.parseBoolean(signDAO.getMaintenance()))
                        .build();
                getAllSigns.put(++id, signObject);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}