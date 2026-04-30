package pl.kul.inpost_assessment.model.parcelLocker;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class ParcelLocker {
    @Id
    private String name;
    private String locationDescription;

    @Enumerated(EnumType.STRING)
    private CountryCode country;

    @Enumerated(EnumType.STRING)
    private ParcelLockerStatus status;
    private double longitude;
    private double latitude;

    @Embedded
    private Address address;
}
