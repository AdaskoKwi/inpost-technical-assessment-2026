package pl.kul.inpost_assessment.network;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.kul.inpost_assessment.DTO.ParcelLockerDTO;
import pl.kul.inpost_assessment.model.parcelLocker.CountryCode;
import pl.kul.inpost_assessment.model.parcelLocker.ParcelLocker;
import pl.kul.inpost_assessment.service.ParcelLockerService;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.ObjectReader;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

@Component
public class ApiClient {
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final ObjectReader objectReader;
    private final ParcelLockerService parcelLockerService;
    private final String API_URL = "https://api-global-points.easypack24.net/v1/points";
    private final int TOTAL_PAGES = 6122;

    @Autowired
    public ApiClient(HttpClient httpClient, ObjectMapper objectMapper, ParcelLockerService parcelLockerService) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.objectReader = objectMapper.readerFor(ApiResponse.class);
        this.parcelLockerService = parcelLockerService;
    }

    public void fetchParcelLockers() {
        List<CompletableFuture<Void>> futures = new ArrayList<>();
        Semaphore semaphore = new Semaphore(200);
        Long start = System.currentTimeMillis();

        for (int i = 1; i <= TOTAL_PAGES; i++) {
            int pageNumber = i;

            try {
                semaphore.acquire();

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(API_URL + "?page=" + pageNumber))
                        .GET()
                        .header("Accept", "application/json")
                        .build();

                CompletableFuture<Void> future = httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                        .thenApply(HttpResponse::body)
                        .thenAccept(this::processJson)
                        .whenComplete((res, ex) -> semaphore.release());

                futures.add(future);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
                .thenRun(() -> {
                    Long finish = System.currentTimeMillis();
                    System.out.println("ApiClient: Przetworzono " + futures.size() + " stron");
                    System.out.println("ApiClient: Zajęło to " + (double)(finish - start) / 1000.0 + " sekund");
                })
                .join();
    }

    private void processJson(String responseBody) {
        try {
            ApiResponse response = objectReader.readValue(responseBody);

            List<ParcelLockerDTO> lockerDTOS = response.items();

            List<ParcelLocker> lockers = lockerDTOS.stream()
                    .filter(dto -> dto.country().equals(CountryCode.PL))
                    .map(ParcelLockerDTO::toEntity)
                    .toList();

            parcelLockerService.saveLockers(lockers);
        } catch (Exception e) {
            System.out.println("Unable to process json: " + e.getMessage());
        }
    }

    private record ApiResponse(
            List<ParcelLockerDTO> items,
            int count,
            @JsonProperty("total_pages") int total_pages
    ) {
    }
}