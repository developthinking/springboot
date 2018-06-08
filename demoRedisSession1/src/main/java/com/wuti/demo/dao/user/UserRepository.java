package com.wuti.demo.dao.user;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.wuti.demo.domain.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	/** 自定义简单查询 */
	User findByUserName(String userName);
	
	User findByUserNameOrEmail(String userName, String email);
	
//	Long deleteById(Long id);

	Long countByUserName(String userName);
	
	List<User> findByEmailLike(String email);

	User findByUserNameIgnoreCase(String userName);

	List<User> findByUserNameOrderByEmailDesc(String email);
	
	/** 复杂查询--分页查询 */
	@Query("select u from User u")
	Page<User> findALL(Pageable pageable);
	
	Page<User> findByUserName(String userName, Pageable pageable);
	
	/** 复杂查询--限制查询 */
//	User findFirstByOrderByLastnameAsc();

//	User findTopByOrderByAgeDesc();

//	Page<User> queryFirst10ByLastname(String lastname, Pageable pageable);

//	List<User> findFirst10ByLastname(String lastname, Sort sort);

//	List<User> findTop10ByLastname(String lastname, Pageable pageable);
	
	/** 自定义SQL查询 */
	@Transactional(timeout = 10)
	@Modifying
	@Query("update User set userName = ?1 where id = ?2")
	int modifyById(String  userName, Long id);

	@Transactional
	@Modifying
	@Query("delete from User where id = ?1")
	void deleteById(Long id);

	@Query("select u from User u where u.email = ?1")
	User findByEmail(String email);
	
}
