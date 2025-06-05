package pl.wsb.fitnesstracker.training.internal;

import org.springframework.stereotype.Service;
import pl.wsb.fitnesstracker.training.api.ActivityType;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.TrainingService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingServiceImpl implements TrainingService {

    private final TrainingRepository trainingRepository;
    private final TrainingMapper trainingMapper;

    public TrainingServiceImpl(TrainingRepository trainingRepository, TrainingMapper trainingMapper) {
        this.trainingRepository = trainingRepository;
        this.trainingMapper = trainingMapper;
    }

    @Override
    public List<TrainingDto> getAllTrainings() {
        return trainingRepository.findAll().stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TrainingDto> getTrainingById(Long id) {
        return trainingRepository.findById(id)
                .map(trainingMapper::toDto);
    }

    @Override
    public List<TrainingDto> getTrainingsByUserId(Long userId) {
        return trainingRepository.findAllByUserId(userId).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> getFinishedAfter(LocalDate date) {
        return trainingRepository.findAllByEndDateAfter(date).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TrainingDto> getByActivity(ActivityType activity) {
        return trainingRepository.findAllByActivityType(activity).stream()
                .map(trainingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TrainingDto createTraining(TrainingDto dto) {
        Training entity = trainingMapper.fromDto(dto);
        Training saved = trainingRepository.save(entity);
        return trainingMapper.toDto(saved);
    }

    @Override
    public TrainingDto updateTraining(Long id, TrainingDto dto) {
        return trainingRepository.findById(id)
                .map(existing -> {
                    existing.setUserId(dto.getUserId());
                    existing.setActivityType(dto.getActivityType());
                    existing.setStartDate(dto.getStartDate());
                    existing.setEndDate(dto.getEndDate());
                    existing.setDistance(dto.getDistance());
                    Training updated = trainingRepository.save(existing);
                    return trainingMapper.toDto(updated);
                })
                .orElseThrow(() -> new IllegalArgumentException("Training not found"));
    }
}
