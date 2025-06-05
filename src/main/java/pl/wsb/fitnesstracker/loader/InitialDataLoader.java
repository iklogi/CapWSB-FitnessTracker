package pl.wsb.fitnesstracker.loader;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserRepository;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.training.internal.TrainingRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class InitialDataLoader {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;

    @PostConstruct
    public void loadData() {
        User u1 = new User("Jan", "Kowalski", LocalDate.of(1990, 1, 1), "jan.kowalski@example.com");
        User u2 = new User("Anna", "Nowak", LocalDate.of(1985, 5, 20), "anna.nowak@example.com");
        userRepository.save(u1);
        userRepository.save(u2);

        Date start1 = Date.from(LocalDate.of(2024, 1, 10).atStartOfDay(ZoneId.of("UTC")).toInstant());
        Date end1 = Date.from(LocalDate.of(2024, 1, 10).atStartOfDay(ZoneId.of("UTC")).toInstant());
        Training t1 = new Training(u1, start1, end1, ActivityType.RUNNING, 5.0, 6.0);
        Training t2 = new Training(u2, start1, end1, ActivityType.WALKING, 3.0, 4.0);
        trainingRepository.save(t1);
        trainingRepository.save(t2);
    }
}