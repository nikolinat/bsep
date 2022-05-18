package com.bsep.admin.app.service.implementation;

import com.bsep.admin.app.repository.LockedAccountRepository;
import com.bsep.admin.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailServiceCustom implements UserDetailsService {
    private UserRepository userRepository;
    private LockedAccountRepository lockedAccountRepository;

    @Autowired
    public UserDetailServiceCustom(UserRepository userRepository, LockedAccountRepository lockedAccountRepository) {
        this.userRepository = userRepository;
        this.lockedAccountRepository = lockedAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = userRepository.findByUsername(username);
        if (userDetails == null) {
            throw new UsernameNotFoundException(username);
        }
        else if(lockedAccountRepository.findByUsername(username) != null && lockedAccountRepository.findByUsername(username).getLoginCounts() >3){
            throw new UsernameNotFoundException(username);
        }

        return userDetails;
    }
}
