package com.kis.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kis.entity.User;
import com.kis.repository.UserRepository;

@Service
public class UserAccountDetailsService implements UserDetailsService {

    @Autowired
    UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.selectByUser(username);
        System.out.println("User:" + user);
        if (user == null)
            throw new UsernameNotFoundException("メールアドレス又はパスワードが違います");

        UserAccountDetails details = new UserAccountDetails(user, this.getAuthorities());
        return details;
    }

    private Collection<GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }
}
