package com.itcast.service;

import java.sql.SQLException;

import com.itcast.dao.UserDao;
import com.itcast.domain.User;

public class UserService {

	public boolean rigist(User user){
		UserDao dao = new UserDao();
		int row=0;
		try {
			row = dao.regist(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return row>0 ? true : false;
	}
//激活用户
	public void active(String activeCode) {
		// TODO Auto-generated method stub
		UserDao dao = new UserDao();
		try {
			dao.active(activeCode);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//用户校验
	public boolean checkUsername(String username) {
		UserDao dao = new UserDao();
		Long isExist = 0L;
		// TODO Auto-generated method stub
		try {
			isExist = dao.checkUsename(username);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isExist>0 ? true:false;
	}

}
