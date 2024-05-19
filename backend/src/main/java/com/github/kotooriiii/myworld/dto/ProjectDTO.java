package com.github.kotooriiii.myworld.dto;

import com.github.kotooriiii.myworld.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Project DTO")
public record ProjectDTO(
        @Schema(description = "Project ID", example = "123e4567-e89b-12d3-a456-426655440000") UUID id,
        @Schema(description = "Project name", example = "My Project") String name,
        @Schema(description = "Project description", example = "This is my project.") String description)
{
}