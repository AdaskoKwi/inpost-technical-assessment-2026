package pl.kul.inpost_assessment.DTO;

import pl.kul.inpost_assessment.model.parcelLocker.ParcelLocker;

import java.util.List;

public record RouteRequestDTO(
    CoordinatesDTO startingPoint,
    List<ParcelLocker> points
) {
    public record CoordinatesDTO(
            double latitude,
            double longitude
    ) {
    }
}
