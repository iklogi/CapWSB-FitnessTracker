package pl.wsb.fitnesstracker.training.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.wsb.fitnesstracker.user.api.UserDto;
import pl.wsb.fitnesstracker.training.internal.ActivityType;

import java.util.Date;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TrainingDto {

    private Long id;

    private UserDto user;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private Date endTime;

    private ActivityType activityType;

    private double distance;

    private double averageSpeed;
}
