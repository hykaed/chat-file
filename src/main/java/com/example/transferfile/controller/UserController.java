package com.example.transferfile.controller;

import com.example.transferfile.dto.UserDto;
import com.example.transferfile.exception.UserException;
import com.example.transferfile.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getAllNonActiveUsers")
    public List<UserDto> getAllNonActiveUsers() {
        return userService.getAllNonActiveUsers();
    }

    @PutMapping("/activateUserById")
    public void activateUserById(@RequestParam Long id) throws UserException {
        userService.activateUserById(id);
    }

    @PutMapping("/deactivateUserById")
    public void deactivateUserById(@RequestParam Long id) throws UserException {
        userService.deactivateUserById(id);
    }

    @PutMapping("/deactivateUsers")
    public List<UserDto> deactivateUsers(@RequestBody List<Long> list) throws UserException {
        return userService.deactivateUsers(list);
    }
}
