package com.projects.travelblog.service;

import com.projects.travelblog.entity.User;
import com.projects.travelblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    public UserServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public List<User> getUsers() {
        return repository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User getUserByEmail(String email) {
        return repository.findByEmail(email);
    }

    @Override
    public String getLoginError() {
        return "Wrong email or password!";
    }

    public String getSignUpError() {
        return "Email already exists!";
    }
}
