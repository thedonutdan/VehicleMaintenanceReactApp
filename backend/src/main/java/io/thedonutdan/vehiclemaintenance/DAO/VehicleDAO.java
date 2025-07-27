package io.thedonutdan.vehiclemaintenance.DAO;

import io.thedonutdan.vehiclemaintenance.model.Vehicle;

import java.util.List;
import java.util.UUID;

public interface VehicleDAO {    
    public boolean insert(Vehicle vehicle);
    public Vehicle findById(UUID id);
    public List<Vehicle> findByUserId(UUID id);
    public Vehicle findByIdAndUserId(UUID vehicleId, UUID userId);
    public boolean update(UUID userId, Vehicle vehicle);
    public boolean delete(UUID userId, UUID id);
}
