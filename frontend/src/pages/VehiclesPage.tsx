import { useEffect, useState } from 'react'
import VehicleList from '../components/VehicleList'
import type { Vehicle } from '../types/Vehicle'

export default function VehiclesPage() {
    const [vehicles, setVehicles] = useState<Vehicle[]>([])

    useEffect(() => {
        setVehicles([
            
        ])
    }, [])

    return (
        <main>
            <h1>Your Vehicles</h1>
            <VehicleList vehicles={vehicles} />
        </main>
    )

}