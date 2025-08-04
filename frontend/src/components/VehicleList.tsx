import type { Vehicle } from "../types/Vehicle"
import VehicleCard from "./VehicleCard"

type Props = {
    vehicles: Vehicle[]
}

export default function VehicleList({ vehicles }: Props) {
    return (
        <ul className="vehicle-list">
            {vehicles.map(vehicle => (
                <VehicleCard key={vehicle.id} vehicle={vehicle} />
            ))}
        </ul>
    )
}