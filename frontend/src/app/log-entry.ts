export class LogEntry {

    constructor(public id: number,
                public tailNumber: string,
                public manufacturer: string,
                public model: string,
                public serialNumber: string,
                public totalTimeOnAirFrame: number,
                public  hobbs: number,
                public workOrderNumber: string,
                public logDate: string,
                public logEntry: string) {
    }
}
