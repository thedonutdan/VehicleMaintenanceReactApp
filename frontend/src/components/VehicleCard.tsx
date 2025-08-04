import type { Vehicle } from "../types/Vehicle"

type Props = {
    vehicle: Vehicle
}

export default function VehicleCard({ vehicle }: Props) {
    return (
        <li className="vehicle-card">
            <h2>{vehicle.year} {vehicle.make} {vehicle.model}</h2>
            <p>VIN: {vehicle.vin}</p>
            <p>Plate: {vehicle.licensePlate}</p>
            <p>Mileage: {vehicle.mileage}</p>
        </li>
    )
}