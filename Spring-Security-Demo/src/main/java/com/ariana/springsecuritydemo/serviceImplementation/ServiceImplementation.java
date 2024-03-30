package com.ariana.springsecuritydemo.serviceImplementation;
import com.ariana.springsecuritydemo.model.User;
import com.ariana.springsecuritydemo.repository.UserRepository;
import com.ariana.springsecuritydemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceImplementation implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public ServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User get(Long id){
        return userRepository.findById(id).get();
    }

    public void delete(Long id){
        userRepository.deleteById(id);
    }

}
