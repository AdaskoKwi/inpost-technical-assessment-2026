package pl.kul.inpost_assessment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.kul.inpost_assessment.model.parcelLocker.ParcelLocker;

public interface ParcelLockerRepository extends JpaRepository<ParcelLocker, String> {
}
