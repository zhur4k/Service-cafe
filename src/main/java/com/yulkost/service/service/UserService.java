package com.yulkost.service.service;

import com.yulkost.service.model.Shift;
import com.yulkost.service.model.User;
import com.yulkost.service.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User activeUserInfo = findByLogin(login);
        if(activeUserInfo == null){
            throw new UsernameNotFoundException("User not authorized.");
        }
        return activeUserInfo;
    }
    public User save(User user) {
        return userRepository.save(user);
    }

    public User findByLogin(String login) {

        return userRepository.findByLogin(login);
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void saveAll(List<User> users) {
        userRepository.saveAllAndFlush(users);
    }

}
