package com.viktoria.viktorifit.user.service;
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
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
      UserEntity user;
      
      if (input.contains("@")) {
        user = userRepository.findByEmailAndIsDeletedFalse(input)
              .orElseThrow(() -> new UsernameNotFoundException("Email not found: " + input));
      } else {
        user = userRepository.findByUsernameAndIsDeletedFalse(input)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + input));
      }
      return User.builder()
              .username(user.getEmail())
              .password(user.getPassword())
              .authorities(user.getRole().name())
              .build(); 
    }
}
