package com.workshop.brianSec.repository;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * @author Brian Barasa
 */

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
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
}
