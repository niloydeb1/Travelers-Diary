package com.projects.travelblog.service;

import com.projects.travelblog.entity.User;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    public List<User> getUsers();

    public User getUserById(int id);

    public User getUserByEmail(String email);

    public String getLoginError();

    public String getSignUpError();
}
