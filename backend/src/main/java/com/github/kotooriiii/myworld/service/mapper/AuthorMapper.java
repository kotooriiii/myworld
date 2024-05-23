package com.github.kotooriiii.myworld.service.mapper;

import com.github.kotooriiii.myworld.dto.AuthorDTO;
import com.github.kotooriiii.myworld.model.Author;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class AuthorMapper implements Function<Author, AuthorDTO> {
    @Override
    public AuthorDTO apply(Author author) {
        return new AuthorDTO(
                author.getId(),
                author.getName(),
                author.getEmail(),
                author.getGender(),
                author.getBirthDate(),
                author.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()),
                author.getUsername(),
                author.getImageIconId()
        );
    }
}
