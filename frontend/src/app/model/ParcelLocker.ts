import {ParcelLockerStatus} from './unionTypes/ParcelLockerStatus';
import {CountryCode} from './unionTypes/CountryCode';
import {Address} from './Address';

export interface ParcelLocker {
    name: string,
    locationDescription: string,
    country: CountryCode,
    status: ParcelLockerStatus,
    longitude: number,
    latitude: number
    address: Address
}
