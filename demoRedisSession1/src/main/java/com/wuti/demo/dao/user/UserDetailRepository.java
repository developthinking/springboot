package com.wuti.demo.dao.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wuti.demo.domain.user.UserDetail;
import com.wuti.demo.domain.user.UserInfo;

public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
	
	/** 多表查询 */
	@Query("select u.userName as userName, u.email as email, d.address as address , d.hobby as hobby from User u , UserDetail d where u.id=d.userId  and  d.hobby = ?1 ")
	List<UserInfo> findUserInfo(String hobby);
}
