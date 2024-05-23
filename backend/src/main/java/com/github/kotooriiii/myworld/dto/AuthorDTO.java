package com.github.kotooriiii.myworld.dto;

import com.github.kotooriiii.myworld.enums.Gender;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record AuthorDTO(
        UUID id,
        String name,
        String email,
        Gender gender,
        LocalDate birthDate,
        List<String> roles,
        String username,
        String imageIconId
){

}
