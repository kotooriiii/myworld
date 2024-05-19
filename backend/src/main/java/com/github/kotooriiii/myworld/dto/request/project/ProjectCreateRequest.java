package com.github.kotooriiii.myworld.dto.request.project;

import com.github.kotooriiii.myworld.enums.Gender;

import java.time.LocalDate;

public record ProjectCreateRequest(
        String title,
        String description)
{
}
