import {Component, EventEmitter, Input, Output} from '@angular/core';
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
    @Output() deleteLocker = new EventEmitter<string>();

    constructor() {
    }
}
