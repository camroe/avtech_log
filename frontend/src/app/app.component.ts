import {Component, OnInit} from '@angular/core';
import {DataService} from "./data.service";
import {LogEntry} from "./log-entry";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
    private logEntries: LogEntry[];
    private selectedLogEntry: LogEntry;

    ngOnInit(): void {
        console.log("ngOnInit");
        this.getLogEntries();
    }

    title = 'AVTECH Maintenance Log ';

    constructor(private dataService: DataService) {
    }

    /**
     * Called on page initialization to populate the potential list of log entries. This is an unnecessary addition but
     * shows an example of populating and selecting a list of items.
     */
    getLogEntries() {
        console.log("calling getLogEntries");
        this.dataService.getLogEntries()
            .subscribe(res => {
                console.log(res);
                this.logEntries = res;
            });
        console.log(this.logEntries);

    }

    onSelect(logEntry: LogEntry): void {
        this.selectedLogEntry = logEntry;
        console.log("Selected LogEntry => ", this.selectedLogEntry);
    }

}
