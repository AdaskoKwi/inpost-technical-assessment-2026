package pl.kul.inpost_assessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.kul.inpost_assessment.model.parcelLocker.ParcelLocker;
import pl.kul.inpost_assessment.repository.ParcelLockerRepository;

import java.util.List;

@Service
public class ParcelLockerService {
    private final ParcelLockerRepository parcelLockerRepository;

    @Autowired
    public ParcelLockerService(ParcelLockerRepository parcelLockerRepository) {
        this.parcelLockerRepository = parcelLockerRepository;
    }

    public void saveLockers(List<ParcelLocker> lockers) {
        parcelLockerRepository.saveAll(lockers);
    }

    public List<ParcelLocker> getAll() {
        return parcelLockerRepository.findAll();
    }

    public List<ParcelLocker> getNearbyLockers(double latitude, double longitude) {
        double longitudeRange = 0.02;
        double latitudeRange = 0.02;

        List<ParcelLocker> nearbyLockers = getAll().stream()
                .filter(locker -> Math.abs(locker.getLatitude() - latitude) <= latitudeRange)
                .filter(locker -> Math.abs(locker.getLongitude() - longitude) <= longitudeRange)
                .toList();

        return nearbyLockers;
    }

    public List<ParcelLocker> getNearbyLockers(double latitude, double longitude, double longitudeRange, double latitudeRange) {
        List<ParcelLocker> nearbyLockers = getAll().stream()
                .filter(locker -> Math.abs(locker.getLatitude() - latitude) <= latitudeRange)
                .filter(locker -> Math.abs(locker.getLongitude() - longitude) <= longitudeRange)
                .toList();

        return nearbyLockers;
    }
}
