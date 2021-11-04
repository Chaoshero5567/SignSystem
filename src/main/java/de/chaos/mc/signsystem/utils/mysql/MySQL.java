package de.chaos.mc.signsystem.utils.mysql;

import de.chaos.mc.signsystem.utils.SignObject;
import de.chaos.mc.signsystem.utils.config.ConfigHandler;

import java.sql.*;
import java.util.HashMap;

public class MySQL {
    private ConfigHandler configHandler;
    public MySQL (ConfigHandler configHandler) {
        this.configHandler = configHandler;
    }
    public Connection connection;
    public HashMap<Long, SignObject> getSign = new HashMap<>();

    public void connectSQL() {
        String host = configHandler.getSQLconfig.getHost();
        Long port = configHandler.getSQLconfig.getPort();
        String user = configHandler.getSQLconfig.getUser();
        String pw = configHandler.getSQLconfig.getPassword();
        String database = configHandler.getSQLconfig.getDatabase();
        String url = "jdbc:mysql://" + host + ":" + port + "/" +  database;
        if (!isConnected()) {
            try {
                connection = DriverManager.getConnection(url, user, pw);
                System.out.println("[MySQL] Verbindung aufgebaut!");
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }
        }

    }

    public void disconnectSQL() {
        if (isConnected()) {
            try {
                connection.close();
                System.out.println("[MySQL] Verbindung wurde getrennt!");
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        }
    }

    public boolean isConnected() {
        return (connection == null ? false : true);
    }

    public void update(String qry) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(qry);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void writeSignIntoDatabase(String world, int x, int y, int z, String server, Boolean maintenance) {
        try {
            String qry = "INSERT INTO signs (WORLD, X, Y, Z, SERVER, MAINTENANCE) values (?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(qry);
            int it = 0;
            preparedStatement.setString(++it, world);
            preparedStatement.setInt(++it, x);
            preparedStatement.setInt(++it, y);
            preparedStatement.setInt(++it, z);
            preparedStatement.setString(++it, server);
            preparedStatement.setString(++it, String.valueOf(maintenance));
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    public ResultSet getResult(String qry) {
        try {
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery(qry);
            resultSet.close();
            return resultSet;
        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
    public void getSigns() {
        try {
            Statement selectStatement = connection.createStatement();
            ResultSet resultSet = selectStatement.executeQuery("SELECT ID,WORLD,X,Y,Z,Server,MAINTENANCE FROM signs WHERE WORLD <= 10");
            while (resultSet.next()) {
                SignObject signObject = SignObject.builder()
                        .id(resultSet.getLong(1))
                        .world(resultSet.getString(2))
                        .x(Integer.valueOf(resultSet.getString(3)))
                        .y(Integer.valueOf(resultSet.getString(4)))
                        .z(Integer.valueOf(resultSet.getString(5)))
                        .Server(resultSet.getString(6))
                        .maintenance(Boolean.parseBoolean(resultSet.getString(7)))
                        .build();
                getSign.put(resultSet.getLong(1), signObject);
            }
            resultSet.close();
            selectStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}