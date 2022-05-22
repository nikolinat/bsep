package com.bsep.admin.app.controller;

import com.bsep.admin.app.dto.CreateUserDto;
import com.bsep.admin.app.dto.SearchFilterUserDto;
import com.bsep.admin.app.dto.UpdateUserDto;
import com.bsep.admin.app.dto.UserDto;
import com.bsep.admin.app.mapper.UserMapper;
import com.bsep.admin.app.model.User;
import com.bsep.admin.app.service.implementation.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin()
@RestController
@RequestMapping(value="/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
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

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer id) throws Exception {
        User user = userService.findById(id);
        UserDto userDto = userMapper.userToUserDto(user);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PostMapping("/new-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserDto userDTO) throws Exception {
        User user = this.userService.create(userDTO);
        return new ResponseEntity<>(this.userMapper.userToUserDto(user), HttpStatus.CREATED);
    }

    @PutMapping(value = "/update-user")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserDto userDTO) throws Exception {
        User user = this.userService.update(userDTO);
        return new ResponseEntity<>(this.userMapper.userToUserDto(user),HttpStatus.OK);
    }

    @RequestMapping(value = "/delete-user/{username}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable (value="username") String username) throws Exception {
        return new ResponseEntity<>(this.userService.delete(username),HttpStatus.OK);
    }
}
