package com.example.transferfile.service.impl;

import com.example.transferfile.auth.jwt.JwtService;
import com.example.transferfile.dto.AuthRequest;
import com.example.transferfile.dto.UserDto;
import com.example.transferfile.enums.Role;
import com.example.transferfile.exception.AuthException;
import com.example.transferfile.exception.UserException;
import com.example.transferfile.mapper.UserMapper;
import com.example.transferfile.model.User;
import com.example.transferfile.repository.UserRepository;
import com.example.transferfile.service.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;
    private final UserMapper userMapper;

    @Override
    public User registerUser(AuthRequest request) throws AuthException {
        Optional<User> userOptional = userRepository.findByUsername(request.getUsername());
        if (userOptional.isEmpty()) {
            User user = new User();
            user.setUsername(request.getUsername());
            user.setPassword(encoder.encode(request.getPassword()));
            user.setRole(Role.ROLE_USER);
            user.setActive(true);
            userRepository.save(user);
            return user;
        } else {
            throw AuthException.usernameTaken();
        }
    }

    @Override
    public User getUserByUsername(String username) throws UserException {
        return userRepository.findByUsername(username)
                .orElseThrow(UserException::userNotFound);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public String loginUser(AuthRequest authRequest) throws AuthException, UserException {
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        authRequest.getUsername(),
                        authRequest.getPassword()
                );

        Authentication authentication = authManager.authenticate(token);
        if (authentication.isAuthenticated()) {
            User user = getUserByUsername(authentication.getName());
            if (!user.isActive()) {
                throw UserException.userNotActive();
            }
            if (user.isLoggedIn()) {
                log.warn("User {} is already logged in", user.getUsername());
                throw AuthException.alreadyLoggedIn();
            } else {
                user.setLoggedIn(true);
                updateUser(user);
                return jwtService.generateToken(authRequest.getUsername());
            }
        } else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }

    @Override
    public void deactivateUserById(Long id) throws UserException {
        User userOptional = userRepository.findById(id)
                .orElseThrow(UserException::userNotFound);
        if (userOptional.isActive()) {
            userOptional.setActive(false);
            userRepository.save(userOptional);
        } else
            throw UserException.userNotActive();
    }

    @Override
    public void activateUserById(Long id) throws UserException {
        User userOptional = userRepository.findById(id)
                .orElseThrow(UserException::userNotFound);
        if (!userOptional.isActive()) {
            userOptional.setActive(true);
            userRepository.save(userOptional);
        } else {
            throw UserException.isAlreadyActive();
        }
    }

    @Override
    public List<UserDto> getAllNonActiveUsers() {
        return userRepository.findAllByActiveIsFalse().stream()
                .map(userMapper::toDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<UserDto> deactivateUsers(List<Long> list) throws UserException {
        if (list == null) {
            return null;
        }
        List<UserDto> userDtos = new ArrayList<>();
        list.forEach(l -> {
            Optional<User> user = userRepository.findById(l);
            if (user.isPresent()) {
                if (user.get().isActive()) {
                    User entity = user.get();
                    entity.setActive(false);
                    userRepository.save(entity);
                    userDtos.add(userMapper.toDto(entity));

                } else {
                    throw UserException.userNotActive();
                }
            } else {
                throw UserException.userNotFound();
            }
        });

        return userDtos;
    }

    @Override
    public void logout() throws UserException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow(UserException::userNotFound);
        user.setLoggedIn(false);
        userRepository.save(user);
    }

    @PostConstruct
    public void init() {
        if (userRepository.findByUsername("admin").isEmpty()) {
            User user = new User();
            user.setUsername("admin");
            user.setPassword(encoder.encode("admin"));
            user.setRole(Role.ROLE_ADMIN);
            user.setActive(true);
            userRepository.save(user);
        }
    }
}
