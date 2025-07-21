package io.thedonutdan.vehiclemaintenance.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDate;
import java.time.Period;

import java.util.List;

import java.io.IOException;

public class VehicleTest {
    private final ObjectMapper mapper = new ObjectMapper()
        .registerModule(new JavaTimeModule());

    @Test
    public void testCreateVehicleNoMaintenance() {
        Vehicle v = new Vehicle("VIN1234", "Honda", "Civic", "555555", 10000);

        assertNotNull(v.getId());
        assertEquals("VIN1234", v.getVIN());
        assertEquals("Honda", v.getMake());
        assertEquals("Civic", v.getModel());
        assertEquals("555555", v.getLicensePlate());
        assertEquals(10000, v.getMileage());
    }

    @Test
    public void testCreateVehicleWithMaintenance() {
        LocalDate date = LocalDate.of(2025, 7, 1);
        ServiceType st = new ServiceType("Oil Change", 5000, Period.ofMonths(6));
        MaintenanceRecord record = new MaintenanceRecord(date, st, 10000, null, null, "5W-30");
        Vehicle v = new Vehicle("VIN1234", "Honda", "Civic", "555555", 10000, List.of(record));

        assertNotNull(v.getId());
        assertEquals("VIN1234", v.getVIN());
        assertEquals("Honda", v.getMake());
        assertEquals("Civic", v.getModel());
        assertEquals("555555", v.getLicensePlate());
        assertEquals(10000, v.getMileage());
        assertEquals(List.of(record), v.getMaintenanceHistory());
    }

    @Test
    public void testAddOldMaintenance() {
        LocalDate date = LocalDate.of(2025, 7, 1);
        ServiceType st = new ServiceType("Oil Change", 5000, Period.ofMonths(6));
        MaintenanceRecord record = new MaintenanceRecord(date, st, 10000, null, null, "5W-30");
        Vehicle v = new Vehicle("VIN1234", "Honda", "Civic", "555555", 100000);

        v.addMaintenance(record);

        List<MaintenanceRecord> history = v.getMaintenanceHistory();
        
        assertEquals(history.get(0), record);
        assertEquals(100000, v.getMileage());
    }

    @Test
    public void testAddNewMaintenance() {
        LocalDate date = LocalDate.of(2025, 7, 1);
        ServiceType st = new ServiceType("Oil Change", 5000, Period.ofMonths(6));
        MaintenanceRecord record = new MaintenanceRecord(date, st, 100000, null, null, "5W-30");
        Vehicle v = new Vehicle("VIN1234", "Honda", "Civic", "555555", 10000);

        v.addMaintenance(record);

        List<MaintenanceRecord> history = v.getMaintenanceHistory();
        
        assertEquals(history.get(0), record);
        assertEquals(100000, v.getMileage());
    }
}