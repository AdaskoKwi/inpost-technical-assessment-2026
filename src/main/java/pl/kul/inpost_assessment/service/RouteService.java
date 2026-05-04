package pl.kul.inpost_assessment.service;

import org.springframework.stereotype.Service;
import pl.kul.inpost_assessment.model.parcelLocker.ParcelLocker;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {
    List<ParcelLocker> route = new ArrayList<>();

    public RouteService() {
    }

    public String getRouteLink(List<ParcelLocker> route) {
        String baseUrl = "https://www.google.com/maps/dir/?api=1";

        route.forEach(System.out::println);

        String origin = getCoordinates(route.getFirst());
        String destination = getCoordinates(route.getLast());

        StringBuilder routeStringBuilder = new StringBuilder()
                .append(baseUrl)
                .append("&origin=")
                .append(origin)
                .append("&destination=")
                .append(destination)
                .append("&waypoints=");

        for (int i = 1; i < route.size()-1; i++) {
            String currentCoordinates = getCoordinates(route.get(i));

            routeStringBuilder
                    .append(currentCoordinates)
                    .append('|');
        }

        this.route = new ArrayList<>();

        return routeStringBuilder.toString();
    }

    public List<ParcelLocker> getRoute(ParcelLocker startingPoint, List<ParcelLocker> lockers) {
        ParcelLocker nearestLocker = new ParcelLocker();
        double minDistance = Double.MAX_VALUE;

        if (route.isEmpty() && lockers.isEmpty()) {
            return List.of();
        }

        route.add(startingPoint);

        if (route.size() == 10 || lockers.isEmpty()) {
            return route;
        } else {
            for (var locker : lockers) {
                double distance = getStraightDistance(startingPoint, locker);
                if (distance <= minDistance) {
                    minDistance = distance;
                    nearestLocker = locker;
                }
            }
            lockers.remove(nearestLocker);
            return getRoute(nearestLocker, lockers);
        }
    }

    private String getCoordinates(ParcelLocker locker) {
        return new StringBuilder()
                .append(locker.getLatitude())
                .append(',')
                .append(locker.getLongitude())
                .toString();
    }

    private double getStraightDistance(ParcelLocker l1, ParcelLocker l2) {
        double lat1 = l1.getLatitude();
        double lat2 = l2.getLatitude();

        double lon1 = l1.getLongitude();
        double lon2 = l2.getLongitude();

        return Math.sqrt(Math.pow((lat1-lat2), 2) + Math.pow((lon1-lon2), 2));
    }
}
