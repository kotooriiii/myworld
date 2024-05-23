package com.github.kotooriiii.myworld.dto.request.author;

import java.time.LocalDate;

public record AuthorUpdateRequest(
        String name,
        String email,
        LocalDate birthDate
) {
}
