package com.bsep.securehome.controller;

import com.bsep.securehome.annotation.LogAfterReturning;
import com.bsep.securehome.annotation.LogAfterThrowing;
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
    @LogAfterThrowing(message = "ERROR search and filter users")
    @LogAfterReturning(message = "SUCCESS search adn filter users")
    @GetMapping("/search-filter")
    public ResponseEntity<List<UserDto>> searchAndFilterUsers(SearchFilterUserDto searchFilterUserDto) {
        List<User> users = userService.searchAndFilterUsers(searchFilterUserDto);
        List<UserDto> usersDto = users.stream().map(user -> userMapper.userToUserDto(user)).collect(Collectors.toList());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WRITE_USERS')")
    @LogAfterThrowing(message = "ERROR create user")
    @LogAfterReturning(message = "SUCCESS create user")
    @PostMapping("/new-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDto userDTO) throws Exception {
        return new ResponseEntity<>(this.userService.create(userDTO), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('WRITE_USERS')")
    @LogAfterThrowing(message = "ERROR update user")
    @LogAfterReturning(message = "SUCCESS update user")
    @PutMapping(value = "/update-user")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUserDto userDTO) throws Exception {
        return new ResponseEntity<>(this.userService.update(userDTO), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_USERS')")
    @LogAfterThrowing(message = "ERROR read user by id")
    @LogAfterReturning(message = "SUCCESS read user by id")
    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id) throws Exception {
        User user = userService.findById(id);
        UserDto userDto = userMapper.userToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('WRITE_USERS')")
    @LogAfterThrowing(message = "ERROR delete user")
    @LogAfterReturning(message = "SUCCESS delete user")
    @RequestMapping(value = "/delete-user/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable(value = "username") String username) throws Exception {
        return new ResponseEntity<>(this.userService.delete(username), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ_USERS')")
    @LogAfterThrowing(message = "ERROR read all users")
    @LogAfterReturning(message = "SUCCESS read all users")
    @GetMapping("")
    public ResponseEntity<List<UserDto>> getUsers() {
        List<User> users = userService.findOwnersAndTenants();
        List<UserDto> usersDto = users.stream().map(user -> userMapper.userToUserDto(user)).collect(Collectors.toList());
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }
}
