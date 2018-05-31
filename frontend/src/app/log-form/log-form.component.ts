import {Component, OnInit} from '@angular/core';
import {LogEntry} from '../log-entry';
import {DataService} from "../data.service";
import {Observable} from "rxjs/index";


@Component({
    selector: 'app-log-form',
    templateUrl: './log-form.component.html',
    styleUrls: ['./log-form.component.css']
})

export class LogFormComponent implements OnInit {

    submitted = false;


    model: LogEntry;
    private ret: Observable<Blob>;

    onSubmit() {
        this.submitted = true;
    }

    constructor(private dataService: DataService) {
    }

    ngOnInit() {
        this.notApplicable();
    }

    // TODO: Remove this when we're done
    get diagnostic() {
        return JSON.stringify(this.model);
    }

    clearForm() {
        this.model = new LogEntry(null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            '',
            null);

    }

    notApplicable() {
        this.model = new LogEntry(null,
            'None',
            'None',
            'None',
            'None',
            0,
            1,
            '1',
            '2018-50-31T12:34:56.12345Z',
            'This is a Default Log Entry');
    }

    submitPDF(logEntry: LogEntry) {
        console.log("CLICK!");
        var pdfBlob: Blob = this.dataService.postLogEntry(logEntry);
        var url: string = window.URL.createObjectURL(pdfBlob);
        window.open(url);
    }
}


