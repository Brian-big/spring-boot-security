package com.workshop.brianSec.repository;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

/**
 * @author Brian Barasa
 */

@Repository
public class UserRepo {
    public static List<UserDetails> applicationUsers = List.of(
            new User(
                    "brian@sec.com",
                    "password",
                    Collections.singleton(
                            new SimpleGrantedAuthority("ROLE_ADMIN")
                    )
            ),
            new User(
                    "user@sec.com",
                    "user",
                    Collections.singleton(
                            new SimpleGrantedAuthority("ROLE_ADMIN")
                    )
            )
    );

    public UserDetails findUserByEmail(String email) {
        return applicationUsers.stream()
                .filter(u -> u.getUsername().equals(email))
                .findFirst()
                .orElseThrow(() ->new UsernameNotFoundException("No user with the provided email found!"));
    }
}
