import {Component, Input} from '@angular/core';
import {LockerCard} from '../locker-card/locker-card';
import {ParcelLocker} from '../../model/ParcelLocker';

@Component({
    selector: 'app-locker-selection',
    imports: [
        LockerCard
    ],
    templateUrl: './locker-selection.html',
    styleUrl: './locker-selection.css',
})
export class LockerSelection {
    @Input() selectedLockers: ParcelLocker[] = [];

    constructor() {
    }

    locker: ParcelLocker = {
        name: 'test',
        locationDescription: 'Przy galerii Plaza',
        country: "PL",
        status: "Operating",
        longitude: 51.23453,
        latitude: 23.41345,
        address: {
            city: 'Lublin',
            province: 'lubelskie',
            postCode: '26-600',
            street: 'Lipowa',
            buildingNumber: '13'
        }
    }
}
