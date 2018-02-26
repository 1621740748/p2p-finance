package cn.itcast.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.user.IUserDAO;
import cn.itcast.domain.user.UserModel;
import cn.itcast.service.user.IUserService;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDAO userDao;

	@Override
	public UserModel findByUsername(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public UserModel findByPhone(String phone) {
		return userDao.findByPhone(phone);
	}

	@Override
	public boolean addUser(UserModel user) {
		UserModel u = userDao.save(user);
		return u != null;
	}

	@Override
	public UserModel login(String username, String pwd) {
		return userDao.login(username, pwd);
	}

	@Override
	public UserModel findById(int userId) {
		return userDao.findOne(userId);
	}

	@Override
	public void updatePhoneStatus(String phone, int id) {
		userDao.updatePhoneStatus(phone, id);
	}

	@Override
	public void updateRealName(String realName, String identity, int i) {

		userDao.updateRealNameStatus(realName, identity, i);
	}

	@Override
	public void addEmail(String email, int id) {
		userDao.addEmail(email, id);
	}

	@Override
	public void updateEmailStatus(int id) {

		userDao.updateEmailStatus(id);
	}

	@Override
	public void save(UserModel user) {
		userDao.save(user);
	}

}
