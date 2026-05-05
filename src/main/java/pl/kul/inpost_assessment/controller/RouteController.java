package pl.kul.inpost_assessment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kul.inpost_assessment.DTO.RouteRequestDTO;
import pl.kul.inpost_assessment.model.parcelLocker.ParcelLocker;
import pl.kul.inpost_assessment.service.RouteService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/route")
public class RouteController {
    RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/link")
    public ResponseEntity<String> getRoute(@RequestBody RouteRequestDTO dto) {
        ParcelLocker startingPoint = ParcelLocker
                .builder()
                .latitude(dto.startingPoint().latitude())
                .longitude(dto.startingPoint().longitude())
                .build();
        List<ParcelLocker> points = new ArrayList<>(dto.points());

        String routeLink = routeService.getRouteLink(startingPoint, points);

        return ResponseEntity.ok(routeLink);
    }
}
