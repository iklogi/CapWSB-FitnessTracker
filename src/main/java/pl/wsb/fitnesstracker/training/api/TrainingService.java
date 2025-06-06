package pl.wsb.fitnesstracker.training.api;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TrainingService {
    List<TrainingDto> getAllTrainings();
    Optional<TrainingDto> getTrainingById(Long id);
    List<TrainingDto> getTrainingsByUserId(Long userId);
    List<TrainingDto> getFinishedAfter(LocalDate date);
    List<TrainingDto> getByActivity(ActivityType activity);
    TrainingDto createTraining(TrainingDto dto);
    TrainingDto updateTraining(Long id, TrainingDto dto);
}
