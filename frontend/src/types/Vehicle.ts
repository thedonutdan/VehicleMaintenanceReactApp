import type { UUID } from './uuid'
import type { MaintenanceRecord } from './MaintenanceRecord'

export interface Vehicle {
    id: UUID
    vin: string
    make: string
    model: string
    year: number
    licensePlate: string
    mileage: number
    maintenanceHistory: MaintenanceRecord[]
}