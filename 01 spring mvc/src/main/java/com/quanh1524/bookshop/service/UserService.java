package com.quanh1524.bookshop.service;

import com.quanh1524.bookshop.domain.Role;
import com.quanh1524.bookshop.domain.User;
import com.quanh1524.bookshop.repository.RoleRepository;
import com.quanh1524.bookshop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String handleHello() {
        return "Hello from Service";
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public User handleSaveUser(User user) {
        if (user.getId() > 0) {
            // Cập nhật User nếu đã có ID
            User existingUser = userRepository.findById(user.getId()).orElse(null);
            if (existingUser != null) {
                existingUser.setFullName(user.getFullName());
                existingUser.setAddress(user.getAddress());
                existingUser.setPhone(user.getPhone());
                existingUser.setAvatar(user.getAvatar());
                existingUser.setRole(user.getRole());
                return userRepository.save(existingUser);
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
