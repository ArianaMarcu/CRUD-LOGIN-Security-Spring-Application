package com.ariana.springsecuritydemo.service;
import com.ariana.springsecuritydemo.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    public User saveUser(User user);
    public List<User> getAllUsers();
    public User get(Long id);
    public void delete(Long id);
}
