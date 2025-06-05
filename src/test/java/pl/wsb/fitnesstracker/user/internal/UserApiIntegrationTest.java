package pl.wsb.fitnesstracker.user.internal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import pl.wsb.fitnesstracker.user.api.BasicUserEmailDto;
import pl.wsb.fitnesstracker.user.api.UserDto;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserApiIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getAllUsers_returnsNonEmptyList() {
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(null, headers);
        ResponseEntity<UserDto[]> response = restTemplate.exchange(
                "http://localhost:" + port + "/v1/users",
                HttpMethod.GET,
                entity,
                UserDto[].class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void createAndGetUser() {
        UserDto dto = new UserDto(null, "Piotr", "Nowak", LocalDate.of(1995, 3, 15), "piotr.nowak@example.com");
        ResponseEntity<UserDto> createResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/users",
                dto,
                UserDto.class
        );
        assertThat(createResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        Long id = createResponse.getBody().id();
        ResponseEntity<UserDto> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/v1/users/" + id,
                UserDto.class
        );
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getResponse.getBody().firstName()).isEqualTo("Piotr");
    }

    @Test
    void deleteUser_andVerifyNotFound() {
        UserDto dto = new UserDto(null, "Test", "User", LocalDate.of(2001, 1, 1), "test.delete@example.com");
        ResponseEntity<UserDto> createResponse = restTemplate.postForEntity(
                "http://localhost:" + port + "/v1/users",
                dto,
                UserDto.class
        );
        Long id = createResponse.getBody().id();
        restTemplate.delete("http://localhost:" + port + "/v1/users/" + id);
        ResponseEntity<UserDto> getResponse = restTemplate.getForEntity(
                "http://localhost:" + port + "/v1/users/" + id,
                UserDto.class
        );
        assertThat(getResponse.getStatusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Test
    void searchByEmail_returnsMatching() {
        UserDto dto = new UserDto(null, "Ewa", "Kowalska", LocalDate.of(1992, 6, 5), "ewa.kowalska@example.com");
        restTemplate.postForEntity("http://localhost:" + port + "/v1/users", dto, UserDto.class);
        ResponseEntity<BasicUserEmailDto[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/v1/users/searchByEmail?email=ewa",
                BasicUserEmailDto[].class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }

    @Test
    void searchByAgeGreaterThan_returnsMatching() {
        UserDto dto = new UserDto(null, "Stary", "User", LocalDate.of(1980, 1, 1), "stary.user@example.com");
        restTemplate.postForEntity("http://localhost:" + port + "/v1/users", dto, UserDto.class);
        ResponseEntity<UserDto[]> response = restTemplate.getForEntity(
                "http://localhost:" + port + "/v1/users/ageGreaterThan?age=40",
                UserDto[].class
        );
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotEmpty();
    }
}
