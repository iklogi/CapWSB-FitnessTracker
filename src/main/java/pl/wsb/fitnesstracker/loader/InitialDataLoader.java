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
        UserDto savedUser = userService.createUser(u1);

        TrainingDto t1 = new TrainingDto(
                null,
                savedUser.id(),
                ActivityType.RUNNING,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 1),
                5.0
        );
        trainingService.createTraining(t1);
    }
}
