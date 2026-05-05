import {AfterViewInit, Component, inject, signal} from '@angular/core';
import * as L from 'leaflet';
import {GeolocationService} from '../../services/geolocation/geolocation.service';
import {LockerSelection} from '../locker-selection/locker-selection';
import {ParcelLocker} from '../../model/ParcelLocker';
import {LockerService} from '../../services/lockers/locker-service';
import {RouteService} from '../../services/route/route-service';
import {StartingPoint} from '../../model/StartingPoint';
import {LinkPopup} from '../link-popup/link-popup/link-popup';

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
        LinkPopup,
    ],
  templateUrl: './map.html',
  styleUrl: './map.css',
})
export class Map implements AfterViewInit {
    private map!: L.Map;
    private leafletMarkers: L.Marker[] = [];
    private geolocationService: GeolocationService = inject(GeolocationService);
    private lockerService: LockerService = inject(LockerService);
    private routeService: RouteService = inject(RouteService);
    private startingPoint: StartingPoint = {latitude: 0, longitude: 0};

    nearbyLockers = signal<ParcelLocker[]>([]);
    selectedLockers = signal<ParcelLocker[]>([]);

    isPopupOpen = signal<boolean>(false)
    popupMessage = signal<string>("");

    routeLink = signal<string>('');

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

    handleRouteLink() {
        if (this.selectedLockers().length > 1) {
            this.getRouteLink();
        } else {
            this.setPopupData(true, "Wybierz co najmniej 2 paczkomaty");
        }
    }

    getRouteLink() {
        this.routeService.getRouteLink(this.startingPoint, this.selectedLockers()).subscribe(link => {
            this.routeLink.set(link);
            this.setPopupData(true, link);
        })
    }

    removeItem(nameToRemove: string) {
        this.selectedLockers.update(currentList =>
            currentList.filter(item => item.name !== nameToRemove)
        );
    }

    private handleLockerSelection(locker: ParcelLocker): void {
        if (this.selectedLockers().length < 9 && !this.selectedLockers().includes(locker)) {
            this.selectedLockers.update(currentList => [...currentList, locker])
        }
    }

    private getCurrentPosition() {
        this.geolocationService.getCurrentPosition().subscribe({
            next: (coords) => {
                const lat = coords.latitude;
                const lng = coords.longitude;

                this.startingPoint.latitude = lat;
                this.startingPoint.longitude = lng;

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

    private setPopupData(isOpen: boolean, link: string) {
        this.isPopupOpen.set(isOpen);
        this.popupMessage.set(link);
    }
}
