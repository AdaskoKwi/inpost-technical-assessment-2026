package pl.kul.inpost_assessment.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import pl.kul.inpost_assessment.model.parcelLocker.ParcelLocker;
import pl.kul.inpost_assessment.testDataFactory.ParcelLockerTestFactory;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class RouteServiceTest {
    @InjectMocks
    RouteService routeService;

    double latitude = 51.24259;
    double longitude = 22.55054;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnEmptyRoute_whenEmptyLockerListPassed() {
        // given
        List<ParcelLocker> emptyLockersList = new ArrayList<>();
        ParcelLocker startingPoint = ParcelLockerTestFactory.createLocker("test", latitude, longitude);

        // when
        List<ParcelLocker> result = routeService.getRoute(startingPoint, emptyLockersList);

        // then
        assertThat(result).isEmpty();
    }

    @Test
    void shouldReturnRoute_whenCorrectSizeLockerListPassed() {
        // given
        List<ParcelLocker> lockers = new ArrayList<>(ParcelLockerTestFactory.createLockersList());
        ParcelLocker startingPoint = ParcelLockerTestFactory.createLocker("test", latitude, longitude);

        // when
        List<ParcelLocker> result = routeService.getRoute(startingPoint, lockers);

        // then
        assertThat(result).hasSize(4);
    }
}