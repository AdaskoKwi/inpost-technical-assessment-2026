package pl.kul.inpost_assessment.scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import pl.kul.inpost_assessment.network.ApiClient;

@Component
public class ScheduledTasks {
    private final ApiClient apiClient;

    @Autowired
    public ScheduledTasks(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Scheduled(cron = "0 0 * * * *")
    public void updateLockersInDatabase() {
        apiClient.fetchParcelLockers();
    }
}
