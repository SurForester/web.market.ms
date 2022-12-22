package org.spring.web.market.services;

import org.spring.web.market.entities.SystemUser;
import org.spring.web.market.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User findByUserName(String username);
    boolean save(SystemUser systemUser);
}
