package com.bsep.securehome.mapper;

import com.bsep.securehome.dto.UserDto;
import com.bsep.securehome.model.User;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDto userToUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getLastName(), user.getUsername(), user.getEmailAddress(),
                user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));
    }
}
