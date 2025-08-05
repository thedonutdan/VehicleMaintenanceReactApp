package io.thedonutdan.vehiclemaintenance.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.thedonutdan.vehiclemaintenance.manager.VehicleManager;
import io.thedonutdan.vehiclemaintenance.model.MaintenanceRecord;
import io.thedonutdan.vehiclemaintenance.model.Vehicle;
import io.thedonutdan.vehiclemaintenance.validation.MaintenanceRecordValidator;
import io.thedonutdan.vehiclemaintenance.validation.VehicleValidator;

import java.util.UUID;

/**
 * Controller for vehicle-related endpoints. Passes validated requests to VehicleManager
 */
@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleManager vehicleManager;

    @Autowired
    public VehicleController(VehicleManager vehicleManager) {
        this.vehicleManager = vehicleManager;
    }

    /**
     * Creates a vehicle in the database
     * @param vehicle the vehicle to add to the database
     * @param userId user id associated with the vehicle (for authorization purposes)
     * @return HTTP 201 if vehicle is created successfully, 400 if vehicle object is malformed
     */
    @PostMapping
    public ResponseEntity<String> createVehicle(@RequestBody Vehicle vehicle, @RequestHeader("X-User-Id") UUID userId) {
        List<String> errors = VehicleValidator.validate(vehicle);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(String.join("\n", errors));
        }

        vehicle.setUserId(userId);
        vehicleManager.addVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vehicle created");
    }

    /**
     * Retrieves a vehicle from the database by vehicle id
     * @param vehicleId id of vehicle to retrieve
     * @param userId user id for validation
     * @return HTTP 404 if vehicle does not exist, vehicle object and HTTP 200 if vehicle is found
     */
    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable UUID vehicleId, @RequestHeader("X-User-Id") UUID userId) {
        Vehicle v = vehicleManager.getVehicleById(vehicleId, userId);
        if (v == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(v);
    }

    /**
     * Retrieves all vehicles associated with a specific user id
     * @param userId user id to retrieve vehicles for
     * @return HTTP 200 and list of vehicles associated with user id
     */
    @GetMapping
    public ResponseEntity<List<Vehicle>> getVehiclesByUserId(@RequestHeader("X-User-Id") UUID userId) {
        List<Vehicle> vehicles = vehicleManager.getVehiclesByUserId(userId);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * Updates a vehicle in the database
     * @param vehicleId id of vehicle to update
     * @param userId user id associated with vehicle for authorization
     * @param vehicle updated vehicle object
     * @return HTTP 400 if vehicle is malformed or vehicle id in request does not match vehicle object, HTTP 403 if vehicle does not exist or is not authorized
     * for that user. HTTP 200 if update is successful.
     */
    @PutMapping("/{vehicleId}")
    public ResponseEntity<String> updateVehicle(
            @PathVariable UUID vehicleId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody Vehicle vehicle
        ) {
        if (!vehicleId.equals(vehicle.getId())) {
            return ResponseEntity.badRequest().body("Vehicle ID mismatch");
        }

        List<String> errors = VehicleValidator.validate(vehicle);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(String.join("\n", errors));
        }

        boolean success = vehicleManager.updateVehicle(userId, vehicle);
        if (!success) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vehicle not found or not authorized");
        }

        return ResponseEntity.ok("Vehicle updated");
    }

    /**
     * Removes a vehicle from the database
     * @param vehicleId id of vehicle to be removed
     * @param userId id of user for authorization
     * @return HTTP 403 if vehicle is not found or user is not authorized. HTTP 200 if removal is successful.
     */
    @DeleteMapping("/{vehicleId}")
    public ResponseEntity<String> deleteVehicle(
            @PathVariable UUID vehicleId,
            @RequestHeader("X-User-Id") UUID userId
        ) {
        boolean success = vehicleManager.deleteVehicle(userId, vehicleId);
        if (!success) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vehicle not found or not authorized");
        }

        return ResponseEntity.ok("Vehicle deleted");
    }

    /**
     * Adds a maintenance record to a vehicle
     * @param vehicleId id of vehicle to be updated
     * @param userId user id for authorization
     * @param record record to be added to vehicle
     * @return HTTP 400 if record is malformed, HTTP 403 if vehicle is not found or user is not authorized. HTTP 200 if successful.
     */
    @PostMapping("/{vehicleId}/maintenance")
    public ResponseEntity<String> addMaintenanceRecord(
            @PathVariable UUID vehicleId,
            @RequestHeader("X-User-Id") UUID userId,
            @RequestBody MaintenanceRecord record
        ) {
        List<String> errors = MaintenanceRecordValidator.validate(record);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(String.join("\n", errors));
        }

        boolean success = vehicleManager.addMaintenanceRecord(userId, vehicleId, record);
        if (!success) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Vehicle not found or not authorized");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("Maintenance record added");
    }
    
}
