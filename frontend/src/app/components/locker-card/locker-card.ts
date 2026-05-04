import {Component, Input} from '@angular/core';
import {ParcelLocker} from '../../model/ParcelLocker';

@Component({
    selector: 'app-locker-card',
    imports: [],
    templateUrl: './locker-card.html',
    styleUrl: './locker-card.css',
})
export class LockerCard {
    @Input() locker!: ParcelLocker;
}
