package de.chaos.mc.signsystem.utils.mysql.dao;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.BaseConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.logging.Logger;

public  class DAOManager<DaoType, KeyType> {
    boolean tablesCreated = false;

    Dao<DaoType, KeyType> dao = null;

    protected BaseConnectionSource connectionSource;
    protected Logger log = Logger.getLogger(DAOManager.class.getName());

    private Class<DaoType> daoType;

    public DAOManager(Class<DaoType> daoType, BaseConnectionSource connectionSource) {
        this.daoType = daoType;
        this.connectionSource = connectionSource;
    }


    public Dao<DaoType, KeyType> getDAO() throws SQLException {
        if (dao == null) {
            dao = DaoManager.createDao(connectionSource, daoType);
        }
        if (!tablesCreated) {
            if (!dao.isTableExists()) {
                TableUtils.createTableIfNotExists(connectionSource, daoType);
                tablesCreated = true;
            }
        }
        return dao;
    }

    public void dropSchema() throws SQLException {
        TableUtils.dropTable(getDAO(), true);
        tablesCreated = false;
    }


}