package com.github.kotooriiii.myworld.dto.request.project;

import java.time.LocalDate;

public record ProjectUpdateRequest(
        String title,
        String description,
        String imageIconId
) {
}
