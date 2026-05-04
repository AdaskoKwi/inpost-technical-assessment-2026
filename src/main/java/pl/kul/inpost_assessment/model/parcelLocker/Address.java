package pl.kul.inpost_assessment.model.parcelLocker;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Embeddable
public class Address {
    private String city;
    private String province;
    private String postCode;
    private String street;
    private String buildingNumber;
}
