package cn.itcast.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.admin.AdminDAO;
import cn.itcast.domain.admin.AdminModel;
import cn.itcast.service.admin.IAdminService;

@Service
public class AdminServiceImpl implements IAdminService {

	@Autowired
	private AdminDAO adminDao;

	public AdminModel login(String username, String password) {
		return adminDao.login(username, password);
	}

}
