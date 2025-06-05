package pl.wsb.fitnesstracker.training;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.user.api.UserDto;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TrainingApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllTrainings_returnsOk() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<TrainingDto[]> response = restTemplate.exchange(
                "http://localhost:" + port + "/v1/trainings",
                HttpMethod.GET,
                entity,
                TrainingDto[].class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createAndGetTraining() {
        UserDto userDto = new UserDto(null, "Adam", "Nowak", LocalDate.of(1990, 2, 2), "adam.nowak@example.com");
        ResponseEntity<UserDto> userResp = restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/users",
                userDto,
                UserDto.class
        );
        Date start = Date.from(LocalDate.of(2024, 1, 15).atStartOfDay(ZoneId.of("UTC")).toInstant());
        Date end = Date.from(LocalDate.of(2024, 1, 15).atStartOfDay(ZoneId.of("UTC")).toInstant());
        TrainingDto dto = new TrainingDto(
                null,
                userResp.getBody(),
                start,
                end,
                pl.wsb.fitnesstracker.training.internal.ActivityType.RUNNING,
                10.0,
                7.0
        );
        ResponseEntity<TrainingDto> createResp = restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/trainings",
                dto,
                TrainingDto.class
        );
        assertThat(createResp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
