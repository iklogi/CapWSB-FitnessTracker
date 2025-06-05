package pl.wsb.fitnesstracker.user.api;

import java.time.LocalDate;

public record UserDto(
        Long id,
        String firstName,
        String lastName,
        LocalDate birthdate,
        String email
) {}