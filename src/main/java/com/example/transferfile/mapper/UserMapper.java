package com.example.transferfile.mapper;

import com.example.transferfile.dto.UserDto;
import com.example.transferfile.model.User;
import org.springframework.stereotype.Component;


public interface UserMapper {
    UserDto toDto(User user);
}
