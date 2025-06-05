package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/trainings")
@RequiredArgsConstructor
public class TrainingController {

    private final TrainingService trainingService;
    private final TrainingMapper trainingMapper;

    @GetMapping
    public ResponseEntity<List<TrainingDto>> getAllTrainings() {
        List<TrainingDto> dtos = trainingService.getAllTrainings().stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<TrainingDto>> getTrainingsByUser(@PathVariable Long userId) {
        List<TrainingDto> dtos = trainingService.getTrainingsByUser(userId).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/finished/{afterTime}")
    public ResponseEntity<List<TrainingDto>> getTrainingsFinishedAfter(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") String afterTime) {

        Date date = ((TrainingServiceImpl) trainingService).parseDateOnly(afterTime);
        List<TrainingDto> dtos = trainingService.getTrainingsFinishedAfter(date).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/activityType")
    public ResponseEntity<List<TrainingDto>> getTrainingsByActivityType(
            @RequestParam("activityType") String activityType) {

        List<TrainingDto> dtos = trainingService.getTrainingsByActivityType(activityType).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    @PostMapping
    public ResponseEntity<TrainingDto> createTraining(@RequestBody TrainingDto dto) {
        Training saved = trainingService.createTraining(trainingMapper.toEntity(dto));
        return ResponseEntity.ok(trainingMapper.toDto(saved));
    }

    @PutMapping("/{trainingId}")
    public ResponseEntity<TrainingDto> updateTraining(
            @PathVariable Long trainingId,
            @RequestBody TrainingDto dto) {

        Training updated = trainingService.updateTraining(trainingId, trainingMapper.toEntity(dto));
        return ResponseEntity.ok(trainingMapper.toDto(updated));
    }
}