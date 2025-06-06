package pl.wsb.fitnesstracker.training.api;

import java.time.LocalDate;

public class TrainingDto {

    private Long id;
    private Long userId;
    private ActivityType activityType;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double distance;

    public TrainingDto() { }

    public TrainingDto(Long id, Long userId, ActivityType activityType, LocalDate startDate, LocalDate endDate, Double distance) {
        this.id = id;
        this.userId = userId;
        this.activityType = activityType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.distance = distance;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public ActivityType getActivityType() {
        return activityType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Double getDistance() {
        return distance;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setActivityType(ActivityType activityType) {
        this.activityType = activityType;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }
}
