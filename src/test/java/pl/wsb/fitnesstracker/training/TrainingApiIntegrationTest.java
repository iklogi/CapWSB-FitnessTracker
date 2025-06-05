package pl.wsb.fitnesstracker.training;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import pl.wsb.fitnesstracker.training.api.ActivityType;
import pl.wsb.fitnesstracker.training.api.TrainingDto;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrainingApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createAndGetTraining() {
        String baseUrl = "http://localhost:" + port + "/v1/trainings";

        TrainingDto newTraining = new TrainingDto(
                (Long) null,
                Long.valueOf(1L),
                ActivityType.RUNNING,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 1),
                Double.valueOf(10.0)
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<TrainingDto> request = new HttpEntity<>(newTraining, headers);

        ResponseEntity<TrainingDto> postResponse =
                restTemplate.postForEntity(baseUrl, request, TrainingDto.class);
        assertThat(postResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        TrainingDto created = postResponse.getBody();
        assertThat(created).isNotNull();
        assertThat(created.getId()).isNotNull();

        ResponseEntity<TrainingDto> getResponse =
                restTemplate.getForEntity(baseUrl + "/" + created.getId(), TrainingDto.class);
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        TrainingDto fetched = getResponse.getBody();
        assertThat(fetched).isNotNull();
        assertThat(fetched.getActivityType()).isEqualTo(ActivityType.RUNNING);
    }
}