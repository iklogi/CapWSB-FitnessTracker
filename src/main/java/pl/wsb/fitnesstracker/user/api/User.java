package pl.wsb.fitnesstracker.user.api;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;

@Entity
@Table(name="users")
@Getter
@NoArgsConstructor
@ToString
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name",nullable=false)
    private String firstName;

    @Column(name="last_name",nullable=false)
    private String lastName;

    @Column(name="birthdate",nullable=false)
    private LocalDate birthdate;

    @Column(name="email",nullable=false,unique=true)
    private String email;

    public User(String firstName,String lastName,LocalDate birthdate,String email) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.birthdate=birthdate;
        this.email=email;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setFirstName(String firstName) {
        this.firstName=firstName;
    }

    public void setLastName(String lastName) {
        this.lastName=lastName;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate=birthdate;
    }

    public void setEmail(String email) {
        this.email=email;
    }
}
