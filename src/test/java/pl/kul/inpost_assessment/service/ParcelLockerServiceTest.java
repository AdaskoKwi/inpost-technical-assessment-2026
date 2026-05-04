package pl.kul.inpost_assessment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import pl.kul.inpost_assessment.model.parcelLocker.ParcelLocker;
import pl.kul.inpost_assessment.repository.ParcelLockerRepository;
import pl.kul.inpost_assessment.testDataFactory.ParcelLockerTestFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class ParcelLockerServiceTest {
    @Mock
    ParcelLockerRepository lockerRepository;

    @InjectMocks
    ParcelLockerService lockerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldSave_AllLockers() {
        // given
        List<ParcelLocker> lockers = ParcelLockerTestFactory.createLockersList();

        // when
        lockerService.saveLockers(lockers);

        //then
        Mockito.verify(lockerRepository, Mockito.times(1)).saveAll(lockers);
    }

    @Test
    void shouldReturn_AllLockers() {
        // given
        List<ParcelLocker> lockers = ParcelLockerTestFactory.createLockersList();
        Mockito.when(lockerRepository.findAll()).thenReturn(lockers);

        // when
        List<ParcelLocker> result = lockerService.getAll();

        // then
        assertThat(result)
                .hasSize(3)
                .containsExactlyElementsOf(lockers);

        Mockito.verify(lockerRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldReturnEmptyList_WhenNoLockersFound() {
        // given
        List<ParcelLocker> emptyLockersList = List.of();
        Mockito.when(lockerRepository.findAll()).thenReturn(emptyLockersList);

        // when
        List<ParcelLocker> result = lockerService.getAll();

        // then
        assertThat(result).isEmpty();

        Mockito.verify(lockerRepository, Mockito.times(1)).findAll();
    }

    @Test
    void shouldReturnLockers_WithinDefaultRange() {
        // given
        List<ParcelLocker> lockers = ParcelLockerTestFactory.createLockersList();
        double latitude = 51.24259;
        double longitude = 22.55054;
        Mockito.when(lockerService.getNearbyLockers(latitude, longitude)).thenReturn(lockers);

        // when
        List<ParcelLocker> result = lockerService.getNearbyLockers(latitude, longitude);

        // then
        assertThat(result)
                .hasSize(3)
                .containsExactlyElementsOf(lockers);
    }

    @Test
    void shouldReturnEmptyList_WhenNoLockersFound_WithinDefaultRange() {
        // given
        List<ParcelLocker> emptyLockersList = List.of();
        double latitude = 41.24259;
        double longitude = 12.55054;
        Mockito.when(lockerService.getNearbyLockers(latitude, longitude)).thenReturn(emptyLockersList);

        // when
        List<ParcelLocker> result = lockerService.getNearbyLockers(latitude, longitude);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnLockers_WithinCustomRange() {
        // given
        List<ParcelLocker> lockers = ParcelLockerTestFactory.createLockersList();
        double latitude = 51.24259;
        double longitude = 22.55054;
        double latitudeRange = 0.045;
        double longitudeRange = 0.045;
        Mockito.when(lockerService.getNearbyLockers(latitude, longitude, longitudeRange, latitudeRange))
                .thenReturn(lockers);

        // when
        List<ParcelLocker> result = lockerService.getNearbyLockers(latitude, longitude, longitudeRange, latitudeRange);

        // then
        assertThat(result)
                .hasSize(3)
                .containsExactlyElementsOf(lockers);
    }

    @Test
    void shouldReturnEmptyList_WhenNoLockersFound_WithinCustomRange() {
        // given
        List<ParcelLocker> emptyLockersList = List.of();
        double latitude = 41.24259;
        double longitude = 12.55054;
        double latitudeRange = 0.045;
        double longitudeRange = 0.045;
        Mockito.when(lockerService.getNearbyLockers(latitude, longitude, longitudeRange, latitudeRange))
                .thenReturn(emptyLockersList);

        // when
        List<ParcelLocker> result = lockerService.getNearbyLockers(latitude, longitude, longitudeRange, latitudeRange);

        // then
        assertThat(result).isEmpty();
    }
}