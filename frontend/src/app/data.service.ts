import {Injectable} from '@angular/core';
import {LogEntry} from "./log-entry";
import {HttpClient, HttpResponse} from "@angular/common/http";

@Injectable({
    providedIn: 'root'
})
export class DataService {

    private logEntriesURL = 'api/logEntries'

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
}
