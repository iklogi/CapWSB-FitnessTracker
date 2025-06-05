package pl.wsb.fitnesstracker.training.internal;

import jakarta.persistence.*;
import pl.wsb.fitnesstracker.user.internal.User;
import java.time.LocalDate;

@Entity
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String activityType;
    private double distance;
    private LocalDate date;
    private boolean finished;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Training() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getActivityType() { return activityType; }
    public void setActivityType(String activityType) { this.activityType = activityType; }

    public double getDistance() { return distance; }
    public void setDistance(double distance) { this.distance = distance; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public boolean isFinished() { return finished; }
    public void setFinished(boolean finished) { this.finished = finished; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
