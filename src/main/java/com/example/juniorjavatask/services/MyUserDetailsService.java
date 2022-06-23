package com.example.juniorjavatask.services;

import com.example.juniorjavatask.ORM.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    DataService dataService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> user = Optional.ofNullable(dataService.findUserEntitiesByUsername(username));
        user.orElseThrow(() -> new UsernameNotFoundException("user not found " + username));
        return user.map(MyUserDetails::new).get();
    }

}
