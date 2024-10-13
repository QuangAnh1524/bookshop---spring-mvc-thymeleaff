package com.quanh1524.bookshop.repository;

import com.quanh1524.bookshop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface UserRepository extends JpaRepository<User, Long> {
    User save(User qanh);

    List<User> findByEmail(String email);

    List<User> findAll();

    User getUserById(long id);

    void deleteUserById(long id);
}
