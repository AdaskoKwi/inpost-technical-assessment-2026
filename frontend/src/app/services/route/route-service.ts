import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {catchError, Observable, throwError} from 'rxjs';
import {StartingPoint} from '../../model/StartingPoint';
import {ParcelLocker} from '../../model/ParcelLocker';

@Injectable({
    providedIn: 'root',
})
export class RouteService {
    private http: HttpClient = inject(HttpClient);
    private readonly ROUTE_API_URL: string = "http://localhost:8080/route";

    getRouteLink(startingPoint: StartingPoint, selectedLockers: ParcelLocker[]): Observable<string> {
        if (selectedLockers.length < 2) {
            console.error("Not enough lockers selected!");
        }
        const url = `${this.ROUTE_API_URL}/link`;

        return this.http.post(url, {
            startingPoint: startingPoint,
            points: selectedLockers
        }, {
            responseType: "text"
        }).pipe(
            catchError(this.handleError)
        )
    }

    private handleError(error: HttpErrorResponse) {
        let errorMessage = 'Unknown error occurred';

        console.error(error);
        return throwError(() => new Error(errorMessage));
    }
}
