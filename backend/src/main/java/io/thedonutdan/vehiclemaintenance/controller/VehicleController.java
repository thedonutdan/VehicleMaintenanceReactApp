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

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final VehicleManager vehicleManager;

    @Autowired
    public VehicleController(VehicleManager vehicleManager) {
        this.vehicleManager = vehicleManager;
    }

    @PostMapping
    public ResponseEntity<String> createVehicle(@RequestBody Vehicle vehicle, @RequestHeader UUID userId) {
        List<String> errors = VehicleValidator.validate(vehicle);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(String.join("\n", errors));
        }

        vehicle.setUserId(userId);
        vehicleManager.addVehicle(vehicle);
        return ResponseEntity.status(HttpStatus.CREATED).body("Vehicle created");
    }

    @GetMapping("/{vehicleId}")
    public ResponseEntity<Vehicle> getVehicle(@PathVariable UUID vehicleId, @RequestHeader("X-User-Id") UUID userId) {
        Vehicle v = vehicleManager.getVehicleById(vehicleId, userId);
        if (v == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(v);
    }

    @GetMapping
    public ResponseEntity<List<Vehicle>> getVehiclesByUserId(@RequestHeader("X-User-Id") UUID userId) {
        List<Vehicle> vehicles = vehicleManager.getVehiclesByUserId(userId);
        return ResponseEntity.ok(vehicles);
    }

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
