import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ParcelLocker} from '../../model/ParcelLocker';

@Component({
    selector: 'app-locker-card',
    imports: [],
    templateUrl: './locker-card.html',
    styleUrl: './locker-card.css',
})
export class LockerCard {
    @Input() locker!: ParcelLocker;

    @Output() deleteLocker = new EventEmitter<string>();

    handleDelete() {
        this.deleteLocker.emit(this.locker.name)
    }
}
