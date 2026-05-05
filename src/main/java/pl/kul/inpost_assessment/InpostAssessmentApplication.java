package pl.kul.inpost_assessment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.kul.inpost_assessment.model.parcelLocker.ParcelLocker;
import pl.kul.inpost_assessment.network.ApiClient;
import pl.kul.inpost_assessment.service.ParcelLockerService;

import java.net.http.HttpClient;
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
    public CommandLineRunner run(ParcelLockerService lockerService, ApiClient apiClient) {
        return args -> {
            List<ParcelLocker> lockers = lockerService.getAll();

            if (lockers.isEmpty()) {
                System.out.println("To się wykonało");
                apiClient.fetchParcelLockers();
            } else {
                System.out.println("To się nie wykonało");
            }
        };
    }
}
