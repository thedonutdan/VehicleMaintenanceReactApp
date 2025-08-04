import type { ServiceType } from './ServiceType.ts'

export interface MaintenanceRecord {
    date: string
    serviceType: ServiceType
    mileage: number
    notes: string
}