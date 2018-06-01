package com.wt.userDemo.domain.param;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UserParam {

	private Long id;
	@NotEmpty(message="姓名不能为空")
	private String userName;
	@Max(value = 100, message = "年龄不能大于 100 岁")
	@Min(value = 12, message = "必须年满 12 岁！")
	private int age;
    private String email;
    private String nickName;
	@NotEmpty(message = "密码不能为空")
	@Length(min = 6, message = "密码长度不能小于 6 位")
	private String password;
    private String regTime;
	private String evaluation;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegTime() {
		return regTime;
	}
	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}
	public String getEvaluation() {
		return evaluation;
	}
	public void setEvaluation(String evaluation) {
		this.evaluation = evaluation;
	}
	
	@Override
	public String toString() {
//		return "UserParam id=" + id + ", userName=" + userName + ", age=" + age + ", email=" + email + ", nickName="
//				+ nickName + ", password=" + password + ", regTime=" + regTime + ", evaluation=" + evaluation;
		return ToStringBuilder.reflectionToString(this);
	}
}
