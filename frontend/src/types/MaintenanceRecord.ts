import type { ServiceType } from './ServiceType.ts'

export interface MaintenanceRecord {
    date: string
    serviceType: ServiceType
    mileage: number
    expiryMileage: number | null
    expiryDate: string | null
    notes: string
}