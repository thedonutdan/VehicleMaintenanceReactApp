package io.thedonutdan.vehiclemaintenance.DAO;

import io.thedonutdan.vehiclemaintenance.model.Vehicle;

import java.util.List;
import java.util.UUID;

public interface VehicleDAO {    
    public void insert(Vehicle vehicle);
    public Vehicle findById(UUID id);
    public List<Vehicle> findByUserId(UUID id);
    public Vehicle findByIdAndUserId(UUID vehicleId, UUID userId);
    public void update(UUID userId, Vehicle vehicle);
    public void delete(UUID userId, UUID id);
}
