import {AfterViewInit, Component, effect, inject, signal} from '@angular/core';
import * as L from 'leaflet';
import {GeolocationService} from '../../services/geolocation/geolocation.service';
import {LockerSelection} from '../locker-selection/locker-selection';
import {ParcelLocker} from '../../model/ParcelLocker';

const iconRetinaUrl = '/assets/marker-icon-2x.png';
const iconUrl = '/assets/marker-icon.png';
const shadowUrl = '/assets/marker-shadow.png';
const defaultIcon = L.icon({
    iconRetinaUrl,
    iconUrl,
    shadowUrl,
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    tooltipAnchor: [16, -28],
    shadowSize: [41, 41]
});
L.Marker.prototype.options.icon = defaultIcon;

@Component({
  selector: 'app-map',
    imports: [
        LockerSelection
    ],
  templateUrl: './map.html',
  styleUrl: './map.css',
})
export class Map implements AfterViewInit {
    private map!: L.Map;
    private leafletMarkers: L.Marker[] = [];
    private geolocationService: GeolocationService = inject(GeolocationService);

    nearbyLockers = signal<ParcelLocker[]>([]);

    constructor() {
        effect(() => {
            if (this.map) {
                this.updateMarkersOnMap(this.nearbyLockers());
            }
        });
    }

    ngAfterViewInit(): void {
        this.initMap();
        this.centerOnUserLocation();
    }

    private initMap(): void {
        this.map = L.map('map', {
            center: [52.2297, 21.0122],
            zoom: 12
        });

        L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
            maxZoom: 18,
            attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        }).addTo(this.map);

        this.map.attributionControl.setPrefix('');
    }

    private updateMarkersOnMap(items: ParcelLocker[]): void {
        this.leafletMarkers.forEach(marker => marker.remove());
        this.leafletMarkers = [];

        items.forEach(item => {
            const marker = L.marker([item.latitude, item.longitude])
                .addTo(this.map)
                .bindPopup(`<b>${item.status}</b><br>${item.locationDescription}`)

            this.leafletMarkers.push(marker);
        });
    }

    private centerOnUserLocation(): void {
        this.geolocationService.getCurrentPosition().subscribe({
            next: (coords) => {
                const lat = coords.latitude;
                const lng = coords.longitude;

                this.map.setView([lat, lng]);
            },
            error: (err) => {
                console.error("Couldn't get geolocation", err);
            }
        })
    }
}
