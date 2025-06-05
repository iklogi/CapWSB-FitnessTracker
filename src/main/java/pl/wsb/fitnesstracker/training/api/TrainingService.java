package pl.wsb.fitnesstracker.training.api;

import java.util.Date;
import java.util.List;

/**
 * Serwis do obsługi CRUD operacji na obiekcie {@link Training}.
 */
public interface TrainingService {

    /**
     * Zwraca listę wszystkich treningów.
     */
    List<Training> getAllTrainings();

    /**
     * Zwraca listę wszystkich treningów przypisanych do użytkownika o danym userId.
     */
    List<Training> getTrainingsByUser(Long userId);

    /**
     * Zwraca listę wszystkich treningów, których endTime > afterDate.
     */
    List<Training> getTrainingsFinishedAfter(Date afterDate);

    /**
     * Zwraca listę wszystkich treningów, których activityType == podany typ.
     */
    List<Training> getTrainingsByActivityType(String activityTypeName);

    /**
     * Zapisuje nowy trening w bazie.
     * Zwraca encję ze wszystkimi ustalonymi polami (w tym ID wygenerowane).
     */
    Training createTraining(Training newTraining);

    /**
     * Aktualizuje istniejący trening o danym ID (jeśli nie istnieje, rzuca wyjątek).
     * Zwraca zaktualizowaną encję.
     */
    Training updateTraining(Long trainingId, Training updatedTraining);
}
