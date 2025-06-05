package pl.wsb.fitnesstracker.training.internal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.ActivityType;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    public TrainingController(TrainingService trainingService) {
        this.trainingService = trainingService;
    }

    @GetMapping
    public List<TrainingDto> getAll() {
        return trainingService.getAllTrainings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingDto> getById(@PathVariable Long id) {
        return trainingService.getTrainingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/byUser/{userId}")
    public List<TrainingDto> getByUser(@PathVariable Long userId) {
        return trainingService.getTrainingsByUserId(userId);
    }

    @GetMapping("/finishedAfter")
    public List<TrainingDto> getFinishedAfter(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return trainingService.getFinishedAfter(date);
    }

    @GetMapping("/byActivity")
    public List<TrainingDto> getByActivity(@RequestParam ActivityType activity) {
        return trainingService.getByActivity(activity);
    }

    @PostMapping
    public TrainingDto create(@RequestBody TrainingDto dto) {
        return trainingService.createTraining(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingDto> update(
            @PathVariable Long id,
            @RequestBody TrainingDto dto) {
        TrainingDto updated = trainingService.updateTraining(id, dto);
        return ResponseEntity.ok(updated);
    }
}
