package com.bsep.admin.app.controller;

import com.bsep.admin.app.dto.SearchFilterUserDto;
import com.bsep.admin.app.dto.UserDto;
import com.bsep.admin.app.mapper.UserMapper;
import com.bsep.admin.app.model.User;
import com.bsep.admin.app.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping(value="/api/v1/users")
public class UserController {
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/search-filter")
    public ResponseEntity<List<UserDto>> searchAndFilterUsers(SearchFilterUserDto searchFilterUserDto) {
        List<User> users = userService.searchAndFilterUsers(searchFilterUserDto);
        List<UserDto> usersDto = users.stream().map(user -> userMapper.userToUserDto(user)).collect(Collectors.toList());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }
}
