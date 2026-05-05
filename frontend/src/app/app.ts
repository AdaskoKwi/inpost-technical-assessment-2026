import { Component, signal } from '@angular/core';
import {Header} from './components/header/header';
import {Map} from './components/map/map';

@Component({
  selector: 'app-root',
    imports: [Header, Map],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('frontend');
}
