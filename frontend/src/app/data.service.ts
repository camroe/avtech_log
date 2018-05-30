import {Injectable} from '@angular/core';
import {LogEntry} from "./log-entry";
import {HttpClient, HttpHeaders,HttpErrorResponse} from "@angular/common/http";
import {Observable,throwError} from "rxjs/index";
import {catchError} from "rxjs/internal/operators";

const postHttpOptions = {
    headers: new HttpHeaders({
        'Content-type': 'application/json'
    })
};


@Injectable({
    providedIn: 'root'
})
export class DataService {

    private logEntriesURL = 'api/logEntries';
    private logEntryURL = 'api/logEntry';


    constructor(private httpClient: HttpClient) {
    }

    //Mock LogEntries in Angular.
    // getLogEntries(): Promise<LogEntry[]> {
    //     return Promise.resolve(LOGENTRIES);
    // }

    getLogEntries() {
        /*
        See the signature of http get() function:
        get(url: string, options?: RequestOptionsArgs): Observable<Response>;
        -> get() returns an Observable object. We can change it to Promise to make an async service by simply using:
        .toPromise() function.

        – response object is a string with json form, so we should transform it to JSON object by using response.json().
        – .catch() is used to handle errors when processing, and we should always implement it to handle exceptions.
         */
        console.log("About to call ", this.logEntriesURL);

        return this.httpClient.get<LogEntry[]>(this.logEntriesURL);
    }

    private handleError(error: any): Promise<any> {
        console.error('Error', error); // for demo purposes only
        return Promise.reject(error.message || error);
    }

    public postLogEntry(logEntry: LogEntry) {
        return this.httpClient.post(this.logEntryURL,logEntry,postHttpOptions)
            .pipe(
                // catchError(this.handleErrorVerbose('postLogEntry',logEntry))
        catchError(this.handleErrorVerbose)
            );
    }
    private handleErrorVerbose(error: HttpErrorResponse) {
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

