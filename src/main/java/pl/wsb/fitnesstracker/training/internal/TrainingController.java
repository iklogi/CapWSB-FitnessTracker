package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.TrainingService;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.internal.TrainingMapper;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Kontroler sieciowy do obsługi CRUD na {@link Training}, ścieżka bazowa /v1/trainings.
 */
@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
class TrainingController {

    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;

    /**
     * GET /v1/trainings
     * Zwraca listę WSZYSTKICH treningów w bazie.
     */
    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings() {
        List<Training> all = trainingService.getAllTrainings();
        List<TrainingDto> dtos = all.stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * GET /v1/trainings/{userId}
     * Zwraca listę treningów dla danego usera (w ścieżce przekazujemy userId).
     */
    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingDto>> getTrainingsByUser(@PathVariable Long userId) {
        List<Training> byUser = trainingService.getTrainingsByUser(userId);
        List<TrainingDto> dtos = byUser.stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * GET /v1/trainings/finished/{afterTime}
     * {afterTime} ma format "yyyy-MM-dd" np. "2024-05-18".
     */
    @GetMapping("/finished/{afterTime}")
    public ResponseEntity<List<TrainingDto>> getTrainingsFinishedAfter(@PathVariable("afterTime")
                                                                       @DateTimeFormat(pattern = "yyyy-MM-dd") String afterTime) {
        // Parsujemy ręcznie przez serwis, bo DateTimeFormat(tag) dla java.util.Date wymaga pełnej strefy czasowej
        Date afterDate = ((TrainingServiceImpl) trainingService).parseDateOnly(afterTime);
        List<Training> byDate = trainingService.getTrainingsFinishedAfter(afterDate);
        List<TrainingDto> dtos = byDate.stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * GET /v1/trainings/activityType?activityType=TENNIS
     * Zwraca listę treningów o podanym typie aktywności (ciąg taki jak TYP ENUMU: RUNNING, CYCLING, TENNIS, itd.).
     */
    @GetMapping("/activityType")
    public ResponseEntity<List<TrainingDto>> getTrainingsByActivityType(
            @RequestParam("activityType") String activityType) {
        List<Training> byType = trainingService.getTrainingsByActivityType(activityType);
        List<TrainingDto> dtos = byType.stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * POST /v1/trainings
     * Tworzy nowy trening. Oczekujemy w ciele JSON zgodnego z TrainingDto
     * (poza polem id, które jest ignorowane).
     */
    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody TrainingDto trainingDto) {
        // Zamiana DTO na encję
        Training toSave = trainingMapper.toEntity(trainingDto);
        Training saved = trainingService.createTraining(toSave);
        // Zwracamy zaktualizowane DTO (z wypełnionym ID i wgranym userem)
        return ResponseEntity.ok(trainingMapper.toDto(saved));
    }

    /**
     * PUT /v1/trainings/{trainingId}
     * Aktualizuje istniejący trening.
     * W treści JSON (TrainingDto) mogą być nowe wartości dla: startTime, endTime, activityType, distance, averageSpeed.
     * Nie zmieniamy usera (zakładamy, że user nie jest modyfikowany przez ten endpoint).
     */
    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingDto> updateTraining(
            @PathVariable("trainingId") Long trainingId,
            @RequestBody TrainingDto trainingDto) {
        // Zamiana DTO na encję
        Training updatedEntity = trainingMapper.toEntity(trainingDto);
        Training saved = trainingService.updateTraining(trainingId, updatedEntity);
        return ResponseEntity.ok(trainingMapper.toDto(saved));
    }

}
