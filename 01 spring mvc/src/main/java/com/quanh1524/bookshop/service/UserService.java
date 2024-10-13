package com.quanh1524.bookshop.service;

import com.quanh1524.bookshop.domain.User;
import com.quanh1524.bookshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String handleHello() {
        return "Hello from Service";
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User handleSaveUser(User user) {
        if (user.getId() > 0) {
            User existingUser = this.userRepository.findById(user.getId()).orElse(null);
            if (existingUser != null) {
                // Cập nhật các trường của existingUser
                existingUser.setFullName(user.getFullName());
                existingUser.setAddress(user.getAddress());
                existingUser.setPhone(user.getPhone());
                // Không cập nhật email và password ở đây
                return this.userRepository.save(existingUser);
            }
        }
        // Nếu không tìm thấy user hiện có, tạo mới
        return this.userRepository.save(user);
    }

    public User getUserById(long id) {
        return this.userRepository.getUserById(id);
    }

    public void deleteUserById(long id) {
        this.userRepository.deleteById(id);
    }
}
