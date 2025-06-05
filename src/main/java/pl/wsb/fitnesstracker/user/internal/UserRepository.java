package pl.wsb.fitnesstracker.user.internal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wsb.fitnesstracker.user.api.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}