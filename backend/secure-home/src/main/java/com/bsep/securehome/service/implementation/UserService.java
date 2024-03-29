package com.bsep.securehome.service.implementation;

import com.bsep.securehome.dto.CreateUserDto;
import com.bsep.securehome.dto.SearchFilterUserDto;
import com.bsep.securehome.dto.UpdateUserDto;
import com.bsep.securehome.exception.BadLogicException;
import com.bsep.securehome.exception.DuplicateEntityException;
import com.bsep.securehome.exception.MissingEntityException;
import com.bsep.securehome.model.Role;
import com.bsep.securehome.model.User;
import com.bsep.securehome.repository.RoleRepository;
import com.bsep.securehome.repository.UserRepository;
import com.bsep.securehome.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(username);
        if (userDetails == null)
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

    public User create(CreateUserDto entity) throws Exception {
        if (userRepository.findByUsername(entity.getUsername()) != null)
            throw new DuplicateEntityException("User with given username already exists.");

        if (userRepository.findByEmailAddress(entity.getEmail()) != null) {
            throw new DuplicateEntityException("User with given email already exists.");
        }

        User user = new User();
        user.setEmailAddress(entity.getEmail());
        user.setLastName(entity.getLastName());
        user.setUsername(entity.getUsername());
        user.setName(entity.getName());

        List<Role> listOfRoles = new ArrayList<>();
        for (String role : entity.getRoles()) {
            Role newRole = roleRepository.findById(Long.parseLong(role)).get();
            listOfRoles.add(newRole);
        }
        user.setRoles(listOfRoles);

        if (PasswordUtil.check(entity.getPassword())) {
            throw new BadLogicException("Password is on list most common passwords, change it.");
        }

        user.setPassword(passwordEncoder.encode(entity.getPassword()));

        userRepository.save(user);

        return user;
    }

    public User save(User entity) {
        return this.userRepository.save(entity);
    }

    public User update(UpdateUserDto entity) throws Exception {

        User user = userRepository.findByUsername(entity.getUsername());
        if (user == null)
            throw new UsernameNotFoundException("User with given username doesn't exists.");

        User userWithEntityEmail = userRepository.findByEmailAddress(entity.getEmail());
        if (userWithEntityEmail != null && userWithEntityEmail.getId() != user.getId()) {
            throw new BadLogicException("Entered email is already in system. Enter another email.");
        }

        user.setEmailAddress(entity.getEmail());
        user.setLastName(entity.getLastName());
        user.setUsername(entity.getUsername());
        user.setName(entity.getName());

        List<Role> listOfRoles = new ArrayList<>();
        for (String role : entity.getRoles()) {
            Role newRole = roleRepository.findByName(role);
            listOfRoles.add(newRole);
        }
        user.setRoles(listOfRoles);

        userRepository.save(user);

        return user;
    }

    public User delete(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new MissingEntityException("User with given username doesn't exists.");

        userRepository.delete(user);
        return user;
    }

    public List<User> findOwnersAndTenants() {
        Role adminRole = roleRepository.getById(1L);
        return userRepository.findAll().stream().filter(user -> !user.getRoles().contains(adminRole))
                .collect(Collectors.toList());
    }

    public List<User> searchAndFilterUsers(SearchFilterUserDto searchFilterUserDto) {
        List<User> users = userRepository.findAll();

        List<Role> roles = new ArrayList<>();
        searchFilterUserDto.getRoles()
                .forEach(role -> roles.add(roleRepository.findById(Long.valueOf(role)).orElse(null)));

        return users.stream().filter(user -> (searchFilterUserDto.getUsername().isEmpty()
                || user.getUsername().contains(searchFilterUserDto.getUsername())) &&
                (searchFilterUserDto.getEmail().isEmpty()
                        || user.getEmailAddress().contains(searchFilterUserDto.getEmail()))
                &&
                (searchFilterUserDto.getName().isEmpty() || user.getName().contains(searchFilterUserDto.getName())) &&
                (searchFilterUserDto.getLastName().isEmpty()
                        || user.getLastName().contains(searchFilterUserDto.getLastName()))
                &&
                (roles.isEmpty() || roles.containsAll(user.getRoles()))).collect(Collectors.toList());
    }

    public User findUser(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("User with given username doesn't exists.");
        return user;
    }

    public List<User> findAdmins() {
        List<User> users = findAll();

        return users.stream().filter(user -> user.getRoles().stream()
                .filter(role -> role.getName().equals("ROLE_ADMIN")).collect(Collectors.toList()).size() > 0)
                .collect(Collectors.toList());
    }
}
