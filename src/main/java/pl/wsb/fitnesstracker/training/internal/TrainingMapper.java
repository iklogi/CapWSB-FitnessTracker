package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.user.internal.UserMapper;


@Component
@RequiredArgsConstructor
public class TrainingMapper {

    private final UserMapper userMapper;

    public TrainingDto toDto(Training entity) {
        if (entity == null) {
            return null;
        }

        return new TrainingDto(
                entity.getId(),
                userMapper.toDto(entity.getUser()),
                entity.getStartTime(),
                entity.getEndTime(),
                entity.getActivityType(),
                entity.getDistance(),
                entity.getAverageSpeed()
        );
    }

    public Training toEntity(TrainingDto dto) {
        if (dto == null) {
            return null;
        }
        var userDto = dto.getUser();
        pl.wsb.fitnesstracker.user.api.User userEntity = new pl.wsb.fitnesstracker.user.api.User();

        Training t = new Training();
        t.setUser(userEntity);
        t.setStartTime(dto.getStartTime());
        t.setEndTime(dto.getEndTime());
        t.setActivityType(dto.getActivityType());
        t.setDistance(dto.getDistance());
        t.setAverageSpeed(dto.getAverageSpeed());
        return t;
    }
}
