package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.dto.SearchFilterUserDto;
import com.bsep.admin.app.exception.MissingEntityException;
import com.bsep.admin.app.model.Role;
import com.bsep.admin.app.model.User;
import com.bsep.admin.app.repository.RoleRepository;
import com.bsep.admin.app.repository.UserRepository;
import com.bsep.admin.app.service.contract.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(username);
        if(userDetails == null)
            throw new UsernameNotFoundException(username);
        return userDetails;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Integer id) throws Exception {
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
            throw new MissingEntityException("User with given id does not exist in the system.");
        return user;
    }

    public User create(User entity) throws Exception {
        return null;
    }

    public User update(User entity, Integer id) throws Exception {
        return null;
    }

    public void delete(Integer id) throws Exception {

    }

    public List<User> findAllAdmins() {
        Role adminRole = roleRepository.getById(1L);
        return userRepository.findAll().stream().filter(user -> user.getRoles().contains(adminRole)).collect(Collectors.toList());
    }

    public List<User> findOwnersAndTenants() {
        Role adminRole = roleRepository.getById(1L);
        return userRepository.findAll().stream().filter(user -> !user.getRoles().contains(adminRole)).collect(Collectors.toList());
    }

    public List<User> searchAndFilterUsers(SearchFilterUserDto searchFilterUserDto) {
        List<User> users = userRepository.findAll();

        List<Role> roles = new ArrayList<>();
        searchFilterUserDto.getRoles().forEach(role -> roles.add(roleRepository.findById(Long.valueOf(role)).orElse(null)));

        return users.stream().filter(user -> (searchFilterUserDto.getUsername().isEmpty() || user.getUsername().contains(searchFilterUserDto.getUsername())) &&
                (searchFilterUserDto.getEmail().isEmpty() || user.getEmailAddress().contains(searchFilterUserDto.getEmail())) &&
                (searchFilterUserDto.getName().isEmpty() || user.getName().contains(searchFilterUserDto.getName())) &&
                (searchFilterUserDto.getLastName().isEmpty() || user.getLastName().contains(searchFilterUserDto.getLastName())) &&
                (roles.isEmpty() || roles.containsAll(user.getRoles()))).collect(Collectors.toList());
    }
}
