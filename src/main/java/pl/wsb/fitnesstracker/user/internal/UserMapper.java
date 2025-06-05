package pl.wsb.fitnesstracker.user.internal;

import pl.wsb.fitnesstracker.user.api.BasicUserEmailDto;
import pl.wsb.fitnesstracker.user.api.User;
import pl.wsb.fitnesstracker.user.api.UserDto;

public class UserMapper {

    public UserDto toDto(User entity) {
        return new UserDto(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getBirthdate(),
                entity.getEmail()
        );
    }

    public User fromDto(UserDto dto) {
        User u = new User(
                dto.firstName(),
                dto.lastName(),
                dto.birthdate(),
                dto.email()
        );
        if (dto.id() != null) {
            u.setId(dto.id());
        }
        return u;
    }

    public BasicUserEmailDto toBasicEmailDto(User entity) {
        return new BasicUserEmailDto(entity.getId(), entity.getEmail());
    }
}
