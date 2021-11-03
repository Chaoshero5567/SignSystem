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
    public HashMap<String, SignObject> getSign = new HashMap<>();

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
            ResultSet resultSet = selectStatement.executeQuery("SELECT WORLD,X,Y,Z,Server FROM signs WHERE WORLD <= 10");
            while (resultSet.next()) {
                SignObject signObject = SignObject.builder()
                        .world(resultSet.getString(1))
                        .x(Integer.valueOf(resultSet.getString(2)))
                        .y(Integer.valueOf(resultSet.getString(3)))
                        .z(Integer.valueOf(resultSet.getString(4)))
                        .Server(resultSet.getString(5))
                        .maintenance(Boolean.parseBoolean(resultSet.getString(6)))
                        .build();
                getSign.put(resultSet.getString(5), signObject);
            }
            resultSet.close();
            selectStatement.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
}