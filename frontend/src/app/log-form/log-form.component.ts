import {Component, OnInit} from '@angular/core';
import {LogEntry} from '../log-entry';
import {DataService} from "../data.service";
import {Observable} from "rxjs/index";
import {HttpErrorResponse} from "@angular/common/http";


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
        // this.dataService.postLogEntry(logEntry);
        // this.ret= this.dataService.postLogEntry(logEntry);
        // console.log("Returned Observable");
        // this.ret.subscribe(response => {
        //     var url: string = window.URL.createObjectURL(this.dataService.postLogEntry(logEntry));
        //     console.log("About to open new window");
        //     window.open(url);
        // });
        // var blob: Blob = new Blob([this.ret],{type:'application/pdf'});
        // var url: string = window.URL.createObjectURL(blob);
        // console.log("About to open new window");
        // console.log(blob);
        // window.open(url);
        // return this.ret;
        var pdfBlob: Blob;
        this.dataService.postLogEntry(logEntry)
            .subscribe(response => {
                    // console.log("About to open new window2");
                    // var blob: Blob = new Blob(["Hello World from log-form!"], {type: "text/plain"});
                    // var url: string = window.URL.createObjectURL(blob);
                    // window.open(url);
                pdfBlob = new Blob([response],{type:"application/pdf"});
                console.log(pdfBlob);
                },
                (err: HttpErrorResponse) => {
                    if (err.error instanceof Error) {
                        console.log("Client-side Error occured");
                    } else {
                        console.log("ServerSide Error Occured");
                        console.log(err.message);
                    }
                });
        // console.log("About to open new window2");
        // var blob: Blob = new Blob(["Hello World from log-form2!"], {type: "text/plain"});
        // var url: string = window.URL.createObjectURL(blob);
        // window.open(url);
        var url:string = window.URL.createObjectURL(pdfBlob);
        window.open(url);
    }
}


