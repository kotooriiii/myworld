package com.github.kotooriiii.myworld.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.kotooriiii.myworld.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Table(name = "authors")
public class Author implements UserDetails, GenericModel
{

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(nullable = false)
    @JsonProperty
    private String name;

    @Column(unique = true, nullable = false)
    @JsonProperty
    @EqualsAndHashCode.Include
    private String email;

    @Column(nullable = false)
    @JsonProperty
    private LocalDate birthDate;

    @Column(nullable = false)
    @JsonProperty
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(nullable = false)
    @JsonProperty
    private String password;

    @Column(unique = true)
    @JsonProperty
    private String imageIconId;

    @OneToMany(mappedBy = "author")
    @ToString.Exclude
    @JsonBackReference
    private Set<ProjectCollaborator> projectCollaborators;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword()
    {
        return this.password;
    }

    @Override
    public String getUsername()
    {
        return email;
    }

    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }

    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }

    @Override
    public boolean isEnabled()
    {
        return true;
    }
}
