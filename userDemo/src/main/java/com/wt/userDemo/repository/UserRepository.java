package com.wt.userDemo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wt.userDemo.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	@Query("select u from User u")
    Page<User> findList(Pageable pageable);
    User findById(long id);
    Long deleteById(Long id);
	User findByUserName(String userName);
    User findByUserNameOrEmail(String userName, String email);
}
