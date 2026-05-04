package pl.kul.inpost_assessment.testDataFactory;

import pl.kul.inpost_assessment.model.parcelLocker.ParcelLocker;
import java.util.List;

public class ParcelLockerTestFactory {
    public static ParcelLocker getDefaultLocker() {
        return ParcelLocker.builder()
                .name("RAD123")
                .latitude(51.24220)
                .longitude(22.55528)
                .build();
    }

    public static ParcelLocker createLocker(String name, double latitude, double longitude) {
        return ParcelLocker.builder()
                .name(name)
                .latitude(latitude)
                .longitude(longitude)
                .build();
    }

    public static List<ParcelLocker> createLockersList() {
        return List.of(
                createLocker("LUB123", 51.24463, 22.55163),
                createLocker("LUB124", 51.23908, 22.54427),
                createLocker("LUB125", 51.23430, 22.54535)
        );
    }
}
