package pl.wsb.fitnesstracker.training.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.wsb.fitnesstracker.training.internal.ActivityType;
import pl.wsb.fitnesstracker.user.api.User;
import java.util.Date;

@Entity
@Table(name="trainings")
@Getter
@NoArgsConstructor
public class Training {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id",nullable=false)
    private User user;

    @Column(name="start_time",nullable=false)
    private Date startTime;

    @Column(name="end_time",nullable=false)
    private Date endTime;

    @Enumerated(EnumType.STRING)
    @Column(name="activity_type",nullable=false)
    private ActivityType activityType;

    @Column(name="distance")
    private double distance;

    @Column(name="average_speed")
    private double averageSpeed;

    public Training(User user,Date startTime,Date endTime,ActivityType activityType,double distance,double averageSpeed) {
        this.user=user;
        this.startTime=startTime;
        this.endTime=endTime;
        this.activityType=activityType;
        this.distance=distance;
        this.averageSpeed=averageSpeed;
    }

    public void setUser(User user) {
        this.user=user;
    }

    public void setStartTime(Date startTime) {
        this.startTime=startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime=endTime;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType=activityType;
    }

    public void setDistance(double distance) {
        this.distance=distance;
    }

    public void setAverageSpeed(double averageSpeed) {
        this.averageSpeed=averageSpeed;
    }
}
