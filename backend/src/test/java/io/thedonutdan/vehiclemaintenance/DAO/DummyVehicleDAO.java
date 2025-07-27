package io.thedonutdan.vehiclemaintenance.DAO;

import io.thedonutdan.vehiclemaintenance.model.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DummyVehicleDAO implements VehicleDAO {
    private List<Vehicle> dummmyDB;

    public DummyVehicleDAO() {
        this.dummmyDB = new ArrayList<>();
    }

    @Override
    public boolean insert(Vehicle vehicle) {
        return dummmyDB.add(vehicle);
    }

    @Override
    public Vehicle findById(UUID id) {
        return dummmyDB.stream()
        .filter(v -> v.getId().equals(id))
        .findFirst()
        .orElse(null);
    }

    @Override
    public List<Vehicle> findByUserId(UUID userId) {
        List<Vehicle> vs = new ArrayList<>();

        for (Vehicle v : dummmyDB) {
            if (v.getUserId().equals(userId)) {
                vs.add(v);
            }
        }

        return vs;
    }

    @Override
    public Vehicle findByIdAndUserId(UUID vehicleId, UUID userId) {
        return dummmyDB.stream()
            .filter(v -> v.getId().equals(vehicleId) && v.getUserId().equals(userId))
            .findFirst()
            .orElse(null);
    }

    @Override
    public boolean update(UUID userId, Vehicle vehicle) {
        return dummmyDB.stream()
            .filter(v -> v.equals(vehicle))
            .findFirst()
            .filter(v -> v.getUserId().equals(userId))
            .map(v -> {
                dummmyDB.remove(v);
                dummmyDB.add(vehicle);
                return true;
            })
            .orElse(false);
    }

    @Override
    public boolean delete(UUID userId, UUID id) {
        return dummmyDB.stream()
        .filter(v -> v.getId().equals(id))
        .findFirst()
        .filter(v -> v.getUserId().equals(userId))
        .map(v -> {
            dummmyDB.remove(v);
            return true;
        })
        .orElse(false);
    }
}
