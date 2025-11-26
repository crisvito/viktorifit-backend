package com.viktoria.viktorifit.user.service;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.viktoria.viktorifit.user.entity.UserEntity;
import com.viktoria.viktorifit.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService{
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      UserEntity userExisting = userRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("User not found with" + email + " not found"));

      return User.builder()
              .username(userExisting.getEmail())
              .password(userExisting.getPassword())
              .authorities(Collections.emptyList())
              .build(); 
    }
}
