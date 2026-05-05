import {inject, Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse} from '@angular/common/http';
import {ParcelLocker} from '../../model/ParcelLocker';
import {catchError, Observable, throwError} from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class LockerService {
    private http: HttpClient = inject(HttpClient);
    private readonly LOCKER_API_URL: string = "http://localhost:8080/lockers";

    getNearbyLockersWithDefaultRange(lat: number, lng: number): Observable<ParcelLocker[]> {
        const url = `${this.LOCKER_API_URL}/nearby/${lat}/${lng}`

        return this.http.get<ParcelLocker[]>(url).pipe(
            catchError(this.handleError)
        );
    }

    getNearbyLockersWithCustomRange(lat: number, lng: number, range: number): Observable<ParcelLocker[]> {
        const url = `${this.LOCKER_API_URL}/nearby/${lat}/${lng}/${range}`

        return this.http.get<ParcelLocker[]>(url).pipe(
            catchError(this.handleError)
        );
    }

    private handleError(error: HttpErrorResponse) {
        let errorMessage = 'Unknown error occurred';

        console.error(error);
        return throwError(() => new Error(errorMessage));
    }
}
