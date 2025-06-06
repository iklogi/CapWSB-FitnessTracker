package pl.wsb.fitnesstracker.loader;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.user.api.UserService;
import pl.wsb.fitnesstracker.training.api.ActivityType;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingService;

import java.time.LocalDate;

@Component
public class InitialDataLoader {

    private final UserService userService;
    private final TrainingService trainingService;

    @Autowired
    public InitialDataLoader(UserService userService, TrainingService trainingService) {
        this.userService = userService;
        this.trainingService = trainingService;
    }

    @PostConstruct
    public void loadData() {
        UserDto u1 = new UserDto(
                null,
                "Jan",
                "Kowalski",
                LocalDate.of(1990, 1, 1),
                "jan.kowalski@example.com"
        );
        UserDto savedUser1 = userService.createUser(u1);

        TrainingDto t1 = new TrainingDto(
                null,
                savedUser1.id(),
                ActivityType.RUNNING,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 1),
                5.0
        );
        trainingService.createTraining(t1);

        UserDto u2 = new UserDto(
                null,
                "Anna",
                "Nowak",
                LocalDate.of(1985, 5, 15),
                "anna.nowak@example.com"
        );
        UserDto savedUser2 = userService.createUser(u2);
        TrainingDto t2 = new TrainingDto(
                null,
                savedUser2.id(),
                ActivityType.BIKING,
                LocalDate.of(2025, 6, 1),
                LocalDate.of(2025, 6, 1),
                20.0
        );
        trainingService.createTraining(t2);

        UserDto u3 = new UserDto(
                null,
                "Piotr",
                "Woźniak",
                LocalDate.of(1992, 3, 22),
                "piotr.wozniak@example.com"
        );
        UserDto savedUser3 = userService.createUser(u3);
        TrainingDto t3 = new TrainingDto(
                null,
                savedUser3.id(),
                ActivityType.SWIMMING,
                LocalDate.of(2025, 6, 2),
                LocalDate.of(2025, 6, 2),
                1.0
        );
        trainingService.createTraining(t3);

        UserDto u4 = new UserDto(
                null,
                "Katarzyna",
                "Kwiatkowska",
                LocalDate.of(1988, 11, 30),
                "kasia.kwiatkowska@example.com"
        );
        UserDto savedUser4 = userService.createUser(u4);
        TrainingDto t4 = new TrainingDto(
                null,
                savedUser4.id(),
                ActivityType.WALKING,
                LocalDate.of(2025, 6, 3),
                LocalDate.of(2025, 6, 3),
                5.0
        );
        trainingService.createTraining(t4);

        UserDto u5 = new UserDto(
                null,
                "Tomasz",
                "Lewandowski",
                LocalDate.of(1995, 7, 8),
                "tomasz.lewandowski@example.com"
        );
        UserDto savedUser5 = userService.createUser(u5);
        TrainingDto t5 = new TrainingDto(
                null,
                savedUser5.id(),
                ActivityType.RUNNING,
                LocalDate.of(2025, 6, 4),
                LocalDate.of(2025, 6, 4),
                10.0
        );
        trainingService.createTraining(t5);

        UserDto u6 = new UserDto(
                null,
                "Ewa",
                "Zielińska",
                LocalDate.of(1979, 9, 14),
                "ewa.zielinska@example.com"
        );
        userService.createUser(u6);
    }
}
