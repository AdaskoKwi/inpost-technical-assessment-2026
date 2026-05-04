package pl.kul.inpost_assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kul.inpost_assessment.model.parcelLocker.ParcelLocker;
import pl.kul.inpost_assessment.service.ParcelLockerService;

import java.util.List;

@RestController
@RequestMapping("/lockers")
public class ParcelLockerController {
    ParcelLockerService lockerService;

    @Autowired
    public ParcelLockerController(ParcelLockerService lockerService) {
        this.lockerService = lockerService;
    }

    @GetMapping("/nearby/{latitude}/{longitude}")
    public ResponseEntity<List<ParcelLocker>> getNearbyLockers(
            @PathVariable double latitude,
            @PathVariable double longitude) {

        return ResponseEntity.ok(lockerService.getNearbyLockers(latitude, longitude));
    }

    @GetMapping("/nearby/{latitude}/{longitude}/{range}")
    public ResponseEntity<List<ParcelLocker>> getNearbyLockers(
            @PathVariable double latitude,
            @PathVariable double longitude,
            @PathVariable double range) {

        return ResponseEntity.ok(lockerService.getNearbyLockers(latitude, longitude, range, range));
    }
}
