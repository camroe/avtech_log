import {Injectable} from '@angular/core';
import {LogEntry} from "./log-entry";
import {LOGENTRIES} from "./mock-logentries";

@Injectable({
    providedIn: 'root'
})
export class DataService {

    constructor() {
    }

    getLogEntries(): Promise<LogEntry[]> {
        return Promise.resolve(LOGENTRIES);
    }
}
