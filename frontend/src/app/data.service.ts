///<reference path="../../node_modules/rxjs/internal/Observable.d.ts"/>
import {Injectable} from '@angular/core';
import {LogEntry} from "./log-entry";
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {Observable, throwError} from "rxjs/index";

const postHttpHeaders = new HttpHeaders({
    'Content-type': 'application/json',
    'Accept': 'application/pdf,*/*'
});


@Injectable({
    providedIn: 'root'
})
export class DataService {

    private logEntriesURL = 'api/logEntries';
    private logEntryURL = 'api/logEntry';
    private ret: Observable<Blob>;
    private blob: Blob;

    constructor(private httpClient: HttpClient) {
    }

    /**
     *
     * @returns {Observable<Object>}
     */
    public getLogEntries() {
        console.log("About to call ", this.logEntriesURL);
        return this.httpClient.get<LogEntry[]>(this.logEntriesURL);
    }

    /**
     * This method will return an observable to the caller to handle.
     * @param {LogEntry} body
     * @returns {Observable<Blob>}
     */
    public postLogEntry(body: LogEntry) {
        console.log("About to call ", this.logEntryURL);
        return this.httpClient.post(this.logEntryURL,
            JSON.stringify(body),
            {
                headers: postHttpHeaders,
                responseType: 'blob'
            });
    }



    private handleErrorVerbose(error: HttpErrorResponse) {
        console.log("In handleErrorVerbose")
        if (error.error instanceof ErrorEvent) {
            // A client-side or network error occurred. Handle it accordingly.
            console.error('An error occurred:', error.error.message);
        } else {
            // The backend returned an unsuccessful response code.
            // The response body may contain clues as to what went wrong,
            console.error(
                `Backend returned code ${error.status}, ` +
                `body was: ${error.error}`);
        }
        // return an observable with a user-facing error message
        return throwError(
            'Something bad happened; please try again later.');
    };

}

