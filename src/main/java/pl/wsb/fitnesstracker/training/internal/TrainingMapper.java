package pl.wsb.fitnesstracker.training.internal;

import pl.wsb.fitnesstracker.training.api.ActivityType;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.training.api.TrainingDto;

public class TrainingMapper {

    public TrainingDto toDto(Training entity) {
        return new TrainingDto(
                entity.getId(),
                entity.getUserId(),
                entity.getActivityType(),
                entity.getStartDate(),
                entity.getEndDate(),
                entity.getDistance()
        );
    }

    public Training fromDto(TrainingDto dto) {
        Training t = new Training(
                dto.getUserId(),
                dto.getActivityType(),
                dto.getStartDate(),
                dto.getEndDate(),
                dto.getDistance()
        );
        if (dto.getId() != null) {
            t.setId(dto.getId());
        }
        return t;
    }
}
