package com.bsep.securehome.controller;

import com.bsep.securehome.dto.CreateUserDto;
import com.bsep.securehome.dto.SearchFilterUserDto;
import com.bsep.securehome.dto.UpdateUserDto;
import com.bsep.securehome.dto.UserDto;
import com.bsep.securehome.mapper.UserMapper;
import com.bsep.securehome.model.User;
import com.bsep.securehome.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private UserService userService;
    private UserMapper userMapper;

    @Autowired
    public UserController(UserService userService,
                          UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PreAuthorize("hasAuthority('READ_USERS')")
    @GetMapping("/search-filter")
    public ResponseEntity<List<UserDto>> searchAndFilterUsers(SearchFilterUserDto searchFilterUserDto) {
        List<User> users = userService.searchAndFilterUsers(searchFilterUserDto);
        List<UserDto> usersDto = users.stream().map(user -> userMapper.userToUserDto(user)).collect(Collectors.toList());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PostMapping("/new-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDto userDTO) throws Exception {
        return new ResponseEntity<>(this.userService.create(userDTO), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDto userDTO) throws Exception {
        return new ResponseEntity<>(this.userService.update(userDTO), HttpStatus.OK);
    }

    @RequestMapping(value = "/delete-user/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable(value = "username") String username) throws Exception {
        return new ResponseEntity<>(this.userService.delete(username), HttpStatus.OK);
    }
}
