package pl.kul.inpost_assessment.DTO;

import pl.kul.inpost_assessment.model.parcelLocker.Address;
import pl.kul.inpost_assessment.model.parcelLocker.CountryCode;
import pl.kul.inpost_assessment.model.parcelLocker.ParcelLocker;
import pl.kul.inpost_assessment.model.parcelLocker.ParcelLockerStatus;

public record ParcelLockerDTO(
        String name,
        String location_description,
        CountryCode country,
        ParcelLockerStatus status,
        LocationDTO location,
        AddressDTO address_details
) {
    public ParcelLocker toEntity() {
        return ParcelLocker.builder()
                .name(this.name)
                .locationDescription(this.location_description)
                .country(this.country)
                .status(this.status)
                .longitude(this.location().longitude())
                .latitude(this.location().latitude())
                .address(Address.builder()
                        .city(address_details.city())
                        .province(address_details.province())
                        .postCode(address_details().post_code())
                        .street(address_details.street())
                        .buildingNumber(address_details().building_number())
                        .build()
                )
                .build();
    }
}
