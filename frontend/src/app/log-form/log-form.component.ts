import {Component, OnInit} from '@angular/core';
import {LogEntry} from '../log-entry';
import {DataService} from "../data.service";


@Component({
    selector: 'app-log-form',
    templateUrl: './log-form.component.html',
    styleUrls: ['./log-form.component.css']
})

export class LogFormComponent implements OnInit {

    submitted = false;
    model: LogEntry;
    private pdfBlob: Blob;

    ngOnInit() {
        this.notApplicable();
    }

    constructor(private dataService: DataService) {
    }


    // TODO: Remove this when we're done
    get diagnostic() {
        return JSON.stringify(this.model);
    }

    onSubmit() {
        this.submitted = true;
    }

    clearForm() {
        this.model = new LogEntry(
            null,
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
        this.model = new LogEntry(
            null,
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
        this.dataService.postLogEntry(logEntry)
            .subscribe(
                response => {
                    this.pdfBlob = new Blob([response], {type: 'application/pdf'});
                    console.log("next .....")
                }, error => {
                    console.log("Error Has Occurred in 'POST logEntry'");
                    console.log(error.toString());
                }, () => {
                    console.log("Message Complete ..... Processing .....");
                    console.log(this.pdfBlob); //This is populated with length 45779
                    const url: string = window.URL.createObjectURL(this.pdfBlob);
                    console.log("About to open URL ", url);
                    var win = window.open(url);
                });
    }
}

/**
 * CODE SNIPPET
 *
 public postLogEntry(body: LogEntry): Blob {
        console.log("postLogEntry: ", this.logEntryURL);
        console.log("body: ", JSON.stringify(body));

        this.httpClient.post(this.logEntryURL, JSON.stringify(body), {
            headers: postHttpHeaders,
            responseType: 'blob'
        }).subscribe(response => {
            this.blob=new Blob([response],{type: 'application/pdf'});
        });
        return this.blob;
    }
 */

