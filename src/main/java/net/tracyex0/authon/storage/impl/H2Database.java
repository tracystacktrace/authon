package net.tracyex0.authon.storage.impl;

import net.tracyex0.authon.storage.IStorage;
import net.tracyex0.authon.storage.PlayerContainer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.*;
import java.util.logging.Logger;


/**
 * Feels like walking in a minefield, innit?
 * <br>
 * I had to figure out some stuff my own
 */
public class H2Database implements IStorage {
    private static final Logger LOGGER_DRIVER = Logger.getLogger("AuthOn H2");

    private final Connection connection;

    public H2Database() {
        try {
            Class.forName("org.h2.Driver");
            this.connection = DriverManager.getConnection("jdbc:h2:./authondb");
            this.setup();
        } catch (SQLException | ClassNotFoundException e) {
            LOGGER_DRIVER.throwing("H2Database", "<init>", e);
            throw new RuntimeException("damn this H2 got fucked up a lil bit ngl");
        }

    }

    public synchronized void setup() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(
                "CREATE TABLE IF NOT EXISTS authon (id INTEGER AUTO_INCREMENT, username VARCHAR(20) NOT NULL, password VARCHAR(100) NOT NULL, ip VARCHAR(40) NOT NULL, CONSTRAINT authon_const_prim PRIMARY KEY (id));"
        );
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public synchronized boolean isPlayerPresent(@NotNull String username) {
        //LOGGER_DRIVER.info("isPlayerPresent(String) -> " + username);
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM authon WHERE username=?;")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            LOGGER_DRIVER.severe("Error while parsing <isPlayerPresent(String)> " + e.getMessage());
            return false;
        }
    }

    @Override
    public synchronized @Nullable PlayerContainer getPlayer(@NotNull String username) {
        //LOGGER_DRIVER.info("getPlayer(String) -> " + username);
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM authon WHERE username=?;")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String dbUsername = resultSet.getString(2);
                String dbHash = resultSet.getString(3);
                String dbIp = resultSet.getString(4);
                if (dbIp.isEmpty()) dbIp = "FUCK.YOU.FUCK.YOU";
                return new PlayerContainer(dbUsername, dbHash, dbIp);
            }
            /* seems like no player */
            return null;
        } catch (SQLException e) {
            LOGGER_DRIVER.severe("Error while parsing <getPlayer(String)> " + e.getMessage());
            return null;
        }
    }

    @Override
    public synchronized boolean savePlayer(@NotNull PlayerContainer player) {
        //LOGGER_DRIVER.info("savePlayer(PlayerContainer) -> " + player);
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO authon (username, password, ip) VALUES (?, ?, ?);")) {
            statement.setString(1, player.getUsername());
            statement.setString(2, player.getHash());
            statement.setString(3, player.getIp());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            /* certified DML fault moment */
            LOGGER_DRIVER.severe("Error while parsing <savePlayer(PlayerContainer)> " + e.getMessage());
            return false;
        }
    }

    @Override
    public synchronized boolean deletePlayer(@NotNull String username) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM authon WHERE username=?;")) {
            statement.setString(1, username);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            /* certified DML fault moment */
            LOGGER_DRIVER.severe("Error while parsing <deletePlayer(String)> " + e.getMessage());
            return false;
        }
    }

    @Override
    public synchronized boolean updateIPAddress(@NotNull PlayerContainer player) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE authon SET ip=? WHERE username=?;")) {
            statement.setString(1, player.getIp());
            statement.setString(2, player.getUsername());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            /* certified DML fault moment */
            LOGGER_DRIVER.severe("Error while parsing <updateIPAdress(PlayerContainer)> " + e.getMessage());
            return false;
        }
    }

    @Override
    public synchronized boolean updatePassword(@NotNull PlayerContainer player) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE authon SET password=? WHERE username=?;")) {
            statement.setString(1, player.getHash());
            statement.setString(2, player.getUsername());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            /* certified DML fault moment */
            LOGGER_DRIVER.severe("Error while parsing <updatePassword(PlayerContainer)> " + e.getMessage());
            return false;
        }
    }
}
