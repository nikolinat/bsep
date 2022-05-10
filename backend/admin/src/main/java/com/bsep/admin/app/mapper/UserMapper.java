package com.bsep.admin.app.mapper;

import com.bsep.admin.app.dto.UserDto;
import com.bsep.admin.app.model.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public UserDto userToUserDto(User user) {
        return new UserDto(user.getName(), user.getLastName(), user.getUsername(), user.getEmailAddress(),
                user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()));
    }
}
