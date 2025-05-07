package com.example.BaseCMS.module.user.service;

import com.example.BaseCMS.module.user.dto.UserDto;
import com.example.BaseCMS.module.user.dto.UserResponse;
import com.example.BaseCMS.module.user.model.User;
import com.example.BaseCMS.module.user.repo.UserRepository;
import com.example.BaseCMS.module.user.rq.UserRq;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
        private final UserRepository userRepository;
        private final ModelMapper modelMapper;
        private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto user =  userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new CustomUserDetails(user);
    }

    public UserResponse create(UserRq rq) {
        String password = passwordEncoder.encode(rq.getPassword());
        User user = User.builder()
                .username(rq.getUsername())
                .password(password)
                .build();
        userRepository.save(user);
        return modelMapper.map(user, UserResponse.class);
    }

    public Page<UserResponse> getAll(Pageable pageable) {
        Page<User> users = userRepository.findAll(pageable);
        return users.map(user -> modelMapper.map(user, UserResponse.class));
    }
}
