package io.thedonutdan.vehiclemaintenance.DAO.impl;

import io.thedonutdan.vehiclemaintenance.DAO.VehicleDAO;
import io.thedonutdan.vehiclemaintenance.model.Vehicle;

import java.sql.Connection;

import java.util.List;
import java.util.UUID;

public class SQLiteVehicleDAO implements VehicleDAO {
    private final Connection conn;

    public SQLiteVehicleDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Vehicle vehicle) {

    }

    @Override
    public Vehicle findById(UUID id) {
        return new Vehicle();
    }

    @Override
    public List<Vehicle> findByUserId(UUID userId) {
        return List.of();
    }

    @Override
    public Vehicle findByIdAndUserId(UUID vehicleId, UUID userId) {
        return new Vehicle();
    }

    @Override
    public void update(UUID userId, Vehicle vehicle) {

    }

    @Override
    public void delete(UUID userId, UUID id) {

    }
}