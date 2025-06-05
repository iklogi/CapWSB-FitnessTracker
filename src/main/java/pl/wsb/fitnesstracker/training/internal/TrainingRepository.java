package pl.wsb.fitnesstracker.training.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wsb.fitnesstracker.training.api.ActivityType;
import pl.wsb.fitnesstracker.training.api.Training;

import java.time.LocalDate;
import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    List<Training> findAllByUserId(Long userId);
    List<Training> findAllByEndDateAfter(LocalDate date);
    List<Training> findAllByActivityType(ActivityType activity);
}
