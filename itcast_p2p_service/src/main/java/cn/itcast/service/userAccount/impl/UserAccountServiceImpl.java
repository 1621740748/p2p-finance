package cn.itcast.service.userAccount.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.userAccount.IUserAccountDAO;
import cn.itcast.domain.userAccount.UserAccountModel;
import cn.itcast.service.userAccount.IUserAccountService;

@Service
public class UserAccountServiceImpl implements IUserAccountService {

	@Autowired
	private IUserAccountDAO userAccountDao;

	@Override
	public void add(int id) {
		UserAccountModel uam = new UserAccountModel();
		uam.setUserId(id);
		userAccountDao.save(uam);
	}

	@Override
	public UserAccountModel findByUserId(int userId) {
		return userAccountDao.findByUserId(userId);
	}

}
