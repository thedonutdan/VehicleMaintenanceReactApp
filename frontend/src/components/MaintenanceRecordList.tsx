import type { MaintenanceRecord } from "../types/MaintenanceRecord"
import MaintenanceRecordCard from "./MaintenanceRecordCard"

type Props = {
    records: MaintenanceRecord[]
}

export default function MaintenanceRecordList({ records }: Props) {
    if (!records || records.length === 0) {
        return <p className="maintenance-records-empty">No maintenance history.</p>
    }

    return (
        <ul className="maintenance-records-list">
            {records.map((record, i) => (
                <MaintenanceRecordCard key={i} record={record} />
            ))}
        </ul>
    )
}