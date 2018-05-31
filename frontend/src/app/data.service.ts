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
    private logEntryURL = 'http://localhost:8090/api/logEntry';
    private ret: Observable<Blob>;

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

    public postLogEntry(body: LogEntry): any {
        console.log("postLogEntry: ", this.logEntryURL);
        console.log("body: ", JSON.stringify(body));

        /**
         post(url: string, body: any | null, options: {
        headers?: HttpHeaders | {
            [header: string]: string | string[];
        };
        observe?: 'body';
        params?: HttpParams | {
            [param: string]: string | string[];
        };
        reportProgress?: boolean;
        responseType: 'blob';
        withCredentials?: boolean;
    }): Observable<Blob>;
         * @type {Observable<any>}
         */
        // this.ret = this.httpClient.post(this.logEntryURL, JSON.stringify(body), {responseType:'blob'})
        //     .pipe(
        //         // catchError(this.handleErrorVerbose('postLogEntry',logEntry))
        //         catchError(this.handleErrorVerbose)
        //     );
        // var blob: Blob = new Blob([this.ret], {type: 'application/pdf'});
        // var url: string = window.URL.createObjectURL(blob);
        // console.log("back from POST CALL ", blob.toString());
        // console.log(blob);
        //
        // console.log(this.ret);
        // return this.ret;
        return this.httpClient.post(this.logEntryURL, JSON.stringify(body), {
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

