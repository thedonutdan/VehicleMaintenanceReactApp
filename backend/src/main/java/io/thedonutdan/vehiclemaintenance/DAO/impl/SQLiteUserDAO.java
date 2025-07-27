package io.thedonutdan.vehiclemaintenance.DAO.impl;

import io.thedonutdan.vehiclemaintenance.DAO.UserDAO;
import io.thedonutdan.vehiclemaintenance.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class SQLiteUserDAO implements UserDAO{
    private final Connection conn;

    public SQLiteUserDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean insert(User user) {
        String query = """
                INSERT INTO users (user_id, username, password_hash) VALUES (?, ?, ?)
                """;
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getUserId().toString());
            stmt.setString(2, user.getUsername());
            stmt.setString(3, user.getPasswordHash());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public User findByUsername(String username) {
        String query = """
                SELECT * FROM users WHERE username = ?
                """;
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(UUID.fromString(rs.getString("user_id")));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public User findById(UUID userId) {
        String query = """
                SELECT * FROM users WHERE user_id = ?
                """;
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId.toString());

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User();
                user.setUserId(UUID.fromString(rs.getString("user_id")));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean delete(UUID userId) {
        String query = """
                DELETE FROM users WHERE user_id = ?
                """;
        
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userId.toString());
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
