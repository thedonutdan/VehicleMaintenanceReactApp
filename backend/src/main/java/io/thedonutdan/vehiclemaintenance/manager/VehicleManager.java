package io.thedonutdan.vehiclemaintenance.manager;

import io.thedonutdan.vehiclemaintenance.DAO.VehicleDAO;
import io.thedonutdan.vehiclemaintenance.model.MaintenanceRecord;
import io.thedonutdan.vehiclemaintenance.model.Vehicle;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehicleManager {
    private final VehicleDAO vehicleDAO;

    @Autowired
    public VehicleManager(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleDAO.insert(vehicle);
    }

    public Vehicle getVehicleById(UUID vehicleId, UUID userId) {
        Vehicle v = vehicleDAO.findById(vehicleId);
        if (!v.getUserId().equals(userId)) {
            return null;
        }
        return v;
    }

    public List<Vehicle> getVehiclesByUserId(UUID userId) {
        return vehicleDAO.findByUserId(userId);
    }

    public boolean updateVehicle(UUID userId, Vehicle vehicle) {
        Vehicle existing = vehicleDAO.findById(vehicle.getId());
        if (existing == null || !existing.getUserId().equals(userId)) {
            return false;
        }
        vehicle.setUserId(userId);
        return vehicleDAO.update(vehicle);
    }

    public boolean deleteVehicle(UUID userId, UUID vehicleId) {
        Vehicle existing = vehicleDAO.findById(vehicleId);
        if (existing == null || !existing.getUserId().equals(userId)) {
            return false;
        }

        return vehicleDAO.delete(vehicleId);
    }

    public boolean addMaintenanceRecord(UUID userId, UUID vehicleId, MaintenanceRecord record) {
        Vehicle vehicle = vehicleDAO.findById(vehicleId);
        if (vehicle == null || !vehicle.getUserId().equals(userId)) {
            return false;
        }

        vehicle.addMaintenance(record);
        return vehicleDAO.update(vehicle);
    }
}
