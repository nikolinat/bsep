package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.dto.CreateUserDto;
import com.bsep.admin.app.dto.SearchFilterUserDto;
import com.bsep.admin.app.exception.DuplicateEntityException;
import com.bsep.admin.app.exception.MissingEntityException;
import com.bsep.admin.app.model.Role;
import com.bsep.admin.app.model.User;
import com.bsep.admin.app.repository.RoleRepository;
import com.bsep.admin.app.repository.UserRepository;
import com.bsep.admin.app.utils.Base64Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
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

//    private byte[] generateSalt() throws NoSuchAlgorithmException {
//        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
//        byte[] salt = new byte[16];
//        sr.nextBytes(salt);
//        return salt;
//    }
//
//    private byte[] hashPassword(String password, byte[] salt) throws InvalidKeySpecException, NoSuchAlgorithmException {
//        int iterations = 1000;
//        char[] chars = password.toCharArray();
//
//        PBEKeySpec spec = new PBEKeySpec(chars, salt, iterations, 64 * 8);
//        SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
//
//        byte[] hash = skf.generateSecret(spec).getEncoded();
//        return hash;
//    }

    public User create(CreateUserDto entity) throws Exception {
        if (userRepository.findByUsername(entity.getUsername()) != null)
            throw new DuplicateEntityException("User with given username already exists.");

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

        user.setPassword(passwordEncoder.encode(entity.getPassword()));
        userRepository.save(user);

        return user;
    }

    public User updateRole(CreateUserDto entity) throws Exception {
        User user = userRepository.findByUsername(entity.getUsername());
        if (user != null) {
            List<String> roles = entity.getRoles();
            List<Role> listOfRoles = new ArrayList<>();
            for (String role : roles) {
                Role newRole = roleRepository.findById(Long.parseLong(role)).get();
                listOfRoles.add(newRole);
            }
            user.setRoles(listOfRoles);

            userRepository.save(user);
        }
        else
            throw new UsernameNotFoundException("User not found");

        return user;
    }

    public User save(User entity) {
        return this.userRepository.save(entity);
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
