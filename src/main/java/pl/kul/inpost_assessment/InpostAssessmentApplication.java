package pl.kul.inpost_assessment;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.kul.inpost_assessment.network.ApiClient;

import java.net.http.HttpClient;
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
    public CommandLineRunner run(ApiClient apiClient) {
        return args -> {
            System.out.println("WAIT FOR API CLIENT TO FINISH FETCHING DATA!!");
            apiClient.fetchParcelLockers();
        };
    }
}
