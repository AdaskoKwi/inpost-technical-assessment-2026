package pl.kul.inpost_assessment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.kul.inpost_assessment.model.parcelLocker.ParcelLocker;
import pl.kul.inpost_assessment.service.ParcelLockerService;
import pl.kul.inpost_assessment.service.RouteService;

import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
public class InpostAssessmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(InpostAssessmentApplication.class, args);
    }

    @Bean
    public HttpClient httpClient() {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .executor(Executors.newVirtualThreadPerTaskExecutor())
                .build();
    }

    @Bean
    public CommandLineRunner run(ParcelLockerService parcelLockerService, RouteService routeService) {
        return args -> {
            ParcelLocker startingPoint = ParcelLocker.builder()
                    .latitude(51.43560)
                    .longitude(21.14700)
                    .build();

            List<ParcelLocker> lockers = parcelLockerService.getNearbyLockers(startingPoint.getLatitude(), startingPoint.getLongitude());

            List<ParcelLocker> newLockers = new ArrayList<>(lockers);

            System.out.println(routeService.getRouteLink(routeService.getRoute(startingPoint, newLockers)));
        };
    }
}
