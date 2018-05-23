import {Component, OnInit} from '@angular/core';
import {DataService} from "./data.service";
import {LogEntry} from "./log-entry";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent  implements OnInit {
    private logEntries: LogEntry[];
 private selectedLogEntry:LogEntry;

    ngOnInit(): void {
        this.getLogEntries();
    }

    title = 'AVTECH Maintenance Log ';

    constructor (private dataService: DataService) {}

    getLogEntries() {
        return this.dataService.getLogEntries().then(logEntries => this.logEntries =logEntries);
    }
    onSelect(logEntry:LogEntry):void {
        this.selectedLogEntry=logEntry;
        console.log("Selected LogEntry => " , this.selectedLogEntry);
    }
}
