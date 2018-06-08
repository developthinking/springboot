package com.wuti.demo.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserDetail {

	private static final long serialVersionUID = 1L;
	@Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, unique = true)
    private Long userId;
    @Column(nullable = true)
    private String address;
    @Column(nullable = true)
    private String hobby;
    
	public UserDetail() {
		super();
	}
	public UserDetail(Long userId, String address, String hobby) {
		super();
		this.userId = userId;
		this.address = address;
		this.hobby = hobby;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getHobby() {
		return hobby;
	}
	public void setHobby(String hobby) {
		this.hobby = hobby;
	}
	
	@Override
	public String toString() {
		return "UserDetail [id=" + id + ", userId=" + userId + ", address=" + address + ", hobby=" + hobby + "]";
	}
}
