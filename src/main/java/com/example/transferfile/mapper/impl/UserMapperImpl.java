package com.example.transferfile.mapper.impl;

import com.example.transferfile.dto.UserDto;
import com.example.transferfile.mapper.UserMapper;
import com.example.transferfile.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDto toDto(User user) {
        if (user == null) {
            return null;
        }
        UserDto userDto = new UserDto();
        if (user.getId() != null)
            userDto.setId(user.getId());
        if (user.getUsername() != null)
            userDto.setUsername(user.getUsername());
        if (user.getPassword() != null)
            userDto.setPassword(user.getPassword());
        userDto.setLoggedIn(userDto.isLoggedIn());
        userDto.setActive(userDto.isActive());
        if (user.getRole() != null)
            userDto.setRole(user.getRole());
        return userDto;
    }
}
