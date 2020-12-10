package com.github.curriculeon.service;

import com.github.curriculeon.model.UserProfile;
import com.github.curriculeon.repository.UserProfileRoleRepository;
import com.github.curriculeon.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserProfileService implements UserDetailsService {
    private UserProfileRepository userRepository;
    private UserProfileRoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserProfileService(UserProfileRepository userRepository, UserProfileRoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserProfile user = userRepository.findByUsername(username);
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        user.getUserRoles().forEach(role -> grantedAuthorities.add(new SimpleGrantedAuthority(role.getName())));
        return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }

    public void save(UserProfile user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserRoles(roleRepository.findAll());
        userRepository.save(user);
    }

    public UserProfile findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
