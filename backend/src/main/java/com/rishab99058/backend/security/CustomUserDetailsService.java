package com.rishab99058.backend.security;

import com.rishab99058.backend.entity.UserEntity;
import com.rishab99058.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity userEntity =  userRepository.findByEmailAndDeletedAtNull(username);
        if(userEntity == null){
            throw new UsernameNotFoundException("No user found with username: " + username);
        }

        return new CustomUserDetails(userEntity);

    }
}
