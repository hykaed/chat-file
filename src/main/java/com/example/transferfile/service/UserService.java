package com.example.transferfile.service;


import com.example.transferfile.dto.AuthRequest;
import com.example.transferfile.dto.UserDto;
import com.example.transferfile.exception.AuthException;
import com.example.transferfile.exception.UserException;
import com.example.transferfile.model.User;

import java.util.List;

public interface UserService {

    User registerUser(AuthRequest request) throws AuthException;

    boolean existsById(Long id);

    User getUserById(Long id) throws UserException;

    User getUserByUsername(String username) throws UserException;

    void updateUser(User user) throws UserException;

    String loginUser(AuthRequest authRequest) throws AuthException, UserException;

    void deactivateUserById(Long id) throws UserException;

    void activateUserById(Long id) throws UserException;

    List<UserDto> getAllNonActiveUsers();

    List<UserDto> deactivateUsers(List<Long> list);

    void logout() throws UserException;
}
