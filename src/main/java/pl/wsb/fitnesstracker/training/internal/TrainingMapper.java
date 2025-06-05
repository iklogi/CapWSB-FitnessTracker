package pl.wsb.fitnesstracker.training.internal;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.training.api.TrainingDto;
import pl.wsb.fitnesstracker.training.api.Training;
import pl.wsb.fitnesstracker.user.internal.UserMapper;
import pl.wsb.fitnesstracker.user.api.User;

@Component
@RequiredArgsConstructor
public class TrainingMapper {

    private final UserMapper userMapper;

    public TrainingDto toDto(Training entity) {
        if (entity==null) return null;
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
        if (dto==null) return null;
        User userEntity=new User();
        userEntity.setId(dto.getUser().id());
        return new Training(
                userEntity,
                dto.getStartTime(),
                dto.getEndTime(),
                dto.getActivityType(),
                dto.getDistance(),
                dto.getAverageSpeed()
        );
    }
}
