package com.itcast.domain;

import java.sql.Date;

public class User {
//	 `uid` varchar(32) NOT NULL,
//	  `username` varchar(20) DEFAULT NULL,
//	  `password` varchar(20) DEFAULT NULL,
//	  `name` varchar(20) DEFAULT NULL,
//	  `email` varchar(30) DEFAULT NULL,
//	  `telephone` varchar(20) DEFAULT NULL,
//	  `birthday` date DEFAULT NULL,
//	  `sex` varchar(10) DEFAULT NULL,
//	  `state` int(11) DEFAULT NULL,
//	  `code` varchar(64) DEFAULT NULL,
//	  PRIMARY KEY (`uid`)
	
	public String uid;
	public String username;
	public String password;
	public String name;
	public String email;
	public String telephone;
	public Date birthday;
	public String sex;
	public Integer state;//激活标志
	public String code;//激活码
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
