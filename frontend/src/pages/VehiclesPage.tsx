import { useEffect, useState } from 'react'
import VehicleList from '../components/VehicleList'
import type { Vehicle } from '../types/Vehicle'

export default function VehiclesPage() {
    const [vehicles, setVehicles] = useState<Vehicle[]>([])

    useEffect(() => {
        setVehicles([
            {
                id: 'uuid-1',
                vin: '123ABC',
                make: 'Toyota',
                model: 'RAV4',
                year: 2015,
                licensePlate: '7LKB652',
                mileage: 100000,
                maintenanceHistory: []
            }
        ])
    }, [])

    return (
        <main>
            <h1>Your Vehicles</h1>
            <VehicleList vehicles={vehicles} />
        </main>
    )

}