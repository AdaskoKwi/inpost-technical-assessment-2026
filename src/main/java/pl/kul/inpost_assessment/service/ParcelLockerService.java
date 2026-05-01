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
}
