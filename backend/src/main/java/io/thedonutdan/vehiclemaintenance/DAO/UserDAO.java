package io.thedonutdan.vehiclemaintenance.DAO;

import java.util.UUID;

import io.thedonutdan.vehiclemaintenance.model.User;

public interface UserDAO {
    boolean insert(User user);
    User findByUsername(String username);
    User findById(UUID userId);
    boolean delete(UUID userId);
}
