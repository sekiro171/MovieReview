package com.example.Group3.confict.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Group3.confict.model.Users;
import com.example.Group3.confict.repository.ReviewRepository;
import com.example.Group3.confict.repository.UserRepository;

@Service
public class UserService {

    private final ReviewRepository reviewRepository;
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.reviewRepository = reviewRepository;
    }

    public boolean addUser(Users user) {
        if (userRepository.existsById(user.getId())) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    public Users getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    public boolean deleteUser(int id) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }

    public boolean updateUser(int id, Users updatedUser) {
        if (!userRepository.existsById(id)) {
            return false;
        }
        updatedUser.setId(id);
        userRepository.save(updatedUser);
        return true;
    }

    public Users login(String userName, String password) {
        System.out.println("Đang đăng nhập: " + userName);
        if (userRepository == null) {
            System.out.println("userRepository NULL!");
            return null;
        }
        Users u = userRepository.findByUserNameAndPassword(userName, password);
        System.out.println("Kết quả: " + (u != null ? "OK" : "NULL"));
        return u;
    }

    public String getRole(Users users) {
        Users u = userRepository.findByUserNameAndPassword(users.getUserName(), users.getPassword());
        if (u != null) {
            return u.getRole();
        }
        return null;
    }

    public boolean register(String userName, String password) {
        if (userName == null || password == null) {
            return false;
        }
        Users newUser = new Users();
        newUser.setUserName(userName);
        newUser.setPassword(password);
        newUser.setRole("User");
        userRepository.save(newUser);
        return true;
    }
}
