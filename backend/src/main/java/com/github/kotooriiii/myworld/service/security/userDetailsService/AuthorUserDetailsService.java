package com.github.kotooriiii.myworld.service.security.userDetailsService;

import com.github.kotooriiii.myworld.dao.AuthorDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorUserDetailsService implements UserDetailsService
{

    private final AuthorDao authorDao;

    public AuthorUserDetailsService(@Qualifier("jdbc") AuthorDao authorDao) {
        this.authorDao = authorDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException
    {
        return authorDao.selectAuthorByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        "Username " + username + " not found"));
    }
}