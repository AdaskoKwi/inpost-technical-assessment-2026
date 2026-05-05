import {Component, input, model} from '@angular/core';

@Component({
    selector: 'app-link-popup',
    imports: [],
    templateUrl: './link-popup.html',
    styleUrl: './link-popup.css',
})
export class LinkPopup {
    isOpen = model(false);
    title = input('Informacja');
    link = input('');
}
