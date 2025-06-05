package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.wsb.fitnesstracker.training.api.TrainingService;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.internal.UserRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementacja serwisu {@link TrainingService}.
 * Używa {@link TrainingRepository} (rozszerza JpaRepository) oraz {@link UserRepository},
 * żeby weryfikować, czy użytkownik istnieje (przy tworzeniu nowego treningu).
 */
@Service
@RequiredArgsConstructor
@Transactional
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final UserRepository userRepository;

    private static final SimpleDateFormat DATE_ONLY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    @Override
    public List<Training> getTrainingsByUser(Long userId) {
        // Filtrujemy strumieniowo: tylko te, gdzie training.getUser().getId() == userId
        return trainingRepository.findAll().stream()
                .filter(t -> t.getUser().getId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Training> getTrainingsFinishedAfter(Date afterDate) {
        return trainingRepository.findAll().stream()
                .filter(t -> t.getEndTime().after(afterDate))
                .collect(Collectors.toList());
    }

    @Override
    public List<Training> getTrainingsByActivityType(String activityTypeName) {
        return trainingRepository.findAll().stream()
                // porównujemy toString() lub name() enumu z przekazaną nazwą (testy podają "TENNIS")
                .filter(t -> t.getActivityType().name().equalsIgnoreCase(activityTypeName))
                .collect(Collectors.toList());
    }

    @Override
    public Training createTraining(Training newTraining) {
        // Najpierw upewnij się, że user istnieje:
        Long userId = newTraining.getUser().getId();
        Optional<User> maybeUser = userRepository.findById(userId);
        if (maybeUser.isEmpty()) {
            throw new IllegalArgumentException("User with id " + userId + " not found");
        }
        // Podstaw prawdziwą encję usera z bazy (żeby Hibernate zadziałał poprawnie)
        newTraining.setUser(maybeUser.get());
        return trainingRepository.save(newTraining);
    }

    @Override
    public Training updateTraining(Long trainingId, Training updatedTraining) {
        Training existing = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new IllegalArgumentException("Training with id " + trainingId + " not found"));

        // Przykładowo: możemy pozwolić na aktualizację dowolnych pól poza ID i Userem.
        // Tutaj przykładowo aktualizujemy wszystkie pola, jakie są w DTO (poza userem – zakładamy, że user się nie zmienia)
        existing.setStartTime(updatedTraining.getStartTime());
        existing.setEndTime(updatedTraining.getEndTime());
        existing.setActivityType(updatedTraining.getActivityType());
        existing.setDistance(updatedTraining.getDistance());
        existing.setAverageSpeed(updatedTraining.getAverageSpeed());

        return trainingRepository.save(existing);
    }

    /**
     * Pomocnicza metoda parsująca parametr ścieżki w formacie yyyy-MM-dd na Date.
     * Używana w kontrolerze.
     */
    public Date parseDateOnly(String dateString) {
        try {
            DATE_ONLY_FORMAT.setLenient(false);
            return DATE_ONLY_FORMAT.parse(dateString);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Niepoprawny format daty: " + dateString + ". Oczekiwany format: yyyy-MM-dd", e);
        }
    }
}
