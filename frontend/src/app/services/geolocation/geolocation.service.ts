import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class GeolocationService {
    constructor() {
    }

    getCurrentPosition(): Observable<GeolocationCoordinates> {
        return new Observable((observer) => {
            if ('geolocation' in navigator) {
                navigator.geolocation.getCurrentPosition((position) => {
                    observer.next(position.coords);
                    observer.complete();
                    }, (error) => {
                    observer.error(error);
                    }
                );
            } else {
                observer.error("Browser does not support geolocation");
            }
        });
    }
}
