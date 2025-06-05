package pl.wsb.fitnesstracker.training.api;

import java.util.Date;
import java.util.List;

public interface TrainingService {
    List<Training> getAllTrainings();
    List<Training> getTrainingsByUser(Long userId);
    List<Training> getTrainingsFinishedAfter(Date afterDate);
    List<Training> getTrainingsByActivityType(String activityTypeName);
    Training createTraining(Training newTraining);
    Training updateTraining(Long trainingId, Training updatedTraining);
}