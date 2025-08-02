package io.thedonutdan.vehiclemaintenance.manager;

import io.thedonutdan.vehiclemaintenance.DAO.VehicleDAO;
import io.thedonutdan.vehiclemaintenance.model.MaintenanceRecord;
import io.thedonutdan.vehiclemaintenance.model.Vehicle;

import java.util.List;
import java.util.UUID;
//TODO add auth here and validation in controller
public class VehicleManager {
    private final VehicleDAO vehicleDAO;

    public VehicleManager(VehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    public void addVehicle(Vehicle vehicle) {
        vehicleDAO.insert(vehicle);
    }

    public Vehicle getVehicleById(UUID vehicleId, UUID userId) {
        return vehicleDAO.findById(vehicleId);
    }

    public List<Vehicle> getVehiclesByUserId(UUID userId) {
        return vehicleDAO.findByUserId(userId);
    }

    public boolean updateVehicle(UUID userId, Vehicle vehicle) {
        return vehicleDAO.update(userId, vehicle);
    }

    public boolean deleteVehicle(UUID userId, UUID vehicleId) {
        return vehicleDAO.delete(userId, vehicleId);
    }

    public boolean addMaintenanceRecord(UUID userId, UUID vehicleId, MaintenanceRecord record) {
        Vehicle vehicle = vehicleDAO.findById(vehicleId);
        if (vehicle == null) {
            return false;
        }

        vehicle.addMaintenance(record);
        return vehicleDAO.update(userId, vehicle);
    }
}
