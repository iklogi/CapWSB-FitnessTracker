package pl.wsb.fitnesstracker.training;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.training.api.ActivityType;
import pl.wsb.fitnesstracker.training.api.TrainingDto;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TrainingApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void createAndGetTraining() {
        UserDto userDto = new UserDto(null, "Adam", "Nowak", LocalDate.of(1990, 2, 2), "adam.nowak@example.com");
        ResponseEntity<UserDto> userResp = restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/users",
                userDto,
                UserDto.class
        );

        assertThat(userResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(userResp.getBody()).isNotNull();

        Date start = Date.from(LocalDate.of(2024, 1, 15).atStartOfDay(ZoneId.of("UTC")).toInstant());
        Date end = Date.from(LocalDate.of(2024, 1, 15).atStartOfDay(ZoneId.of("UTC")).toInstant());
        TrainingDto dto = new TrainingDto(
                null,
                userResp.getBody().id(),
                ActivityType.RUNNING,
                LocalDate.of(2024, 1, 15),
                LocalDate.of(2024, 1, 15),
                10.0
        );
        ResponseEntity<TrainingDto> createResp = restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/trainings",
                dto,
                TrainingDto.class
        );

        assertThat(createResp.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(createResp.getBody()).isNotNull();
        assertThat(createResp.getBody().getUserId()).isNotNull();
    }
}
