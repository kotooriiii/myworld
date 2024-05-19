package com.github.kotooriiii.myworld.dto.request.author;

import com.github.kotooriiii.myworld.enums.Gender;

import java.time.LocalDate;

public record AuthorRegistrationRequest(
        String name,
        String email,
        String password,
        LocalDate birthDate,
        Gender gender
) {
}
