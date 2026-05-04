import {AfterViewInit, Component, inject, signal} from '@angular/core';
import * as L from 'leaflet';
import {GeolocationService} from '../../services/geolocation/geolocation.service';
import {LockerSelection} from '../locker-selection/locker-selection';
import {ParcelLocker} from '../../model/ParcelLocker';
import {LockerService} from '../../services/lockers/locker-service';

const iconRetinaUrl = 'marker-icon.png';
const iconUrl = 'marker-icon-2x.png';
const shadowUrl = 'marker-shadow.png';
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
        LockerSelection,
    ],
  templateUrl: './map.html',
  styleUrl: './map.css',
})
export class Map implements AfterViewInit {
    private map!: L.Map;
    private leafletMarkers: L.Marker[] = [];
    private geolocationService: GeolocationService = inject(GeolocationService);
    private lockerService: LockerService = inject(LockerService);

    nearbyLockers = signal<ParcelLocker[]>([]);
    selectedLockers = signal<ParcelLocker[]>([]);

    constructor() {
    }

    ngAfterViewInit(): void {
        this.initMap();
        this.getCurrentPosition()
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
            const marker = L.marker([item.latitude, item.longitude]) as L.Marker & {data?: ParcelLocker};

            marker.data = item;
            marker.addTo(this.map);
            marker.bindPopup(`<b>Paczkomat InPost, ${item.address.street} ${item.address.buildingNumber}</b><br>${item.locationDescription}`);

            marker.on('click', (event) => {
                this.handleLockerSelection(marker.data!);
            })

            this.leafletMarkers.push(marker);
        });
    }

    removeItem(nameToRemove: string) {
        this.selectedLockers.update(currentList =>
            currentList.filter(item => item.name !== nameToRemove)
        );
    }

    private handleLockerSelection(locker: ParcelLocker): void {
        if (this.selectedLockers().length < 9) {
            this.selectedLockers.update(currentList => [...currentList, locker])
        }
    }

    private getCurrentPosition() {
        this.geolocationService.getCurrentPosition().subscribe({
            next: (coords) => {
                const lat = coords.latitude;
                const lng = coords.longitude;

                this.centerOnUserLocation(lat, lng);
                this.loadLockers(lat, lng)
        }, error: (err) => {
                console.error("Couldn't get geolocation", err)
            }
        })
    }

    private centerOnUserLocation(lat: number, lng: number): void {
        this.map.setView([lat, lng]);
    }

    private loadLockers(lat: number, lng: number) {
        this.lockerService.getNearbyLockersWithDefaultRange(lat, lng).subscribe(lockers => {
            this.nearbyLockers.set(lockers);
            console.log(this.nearbyLockers)
            this.updateMarkersOnMap(this.nearbyLockers());
        });
    }
}
