package cn.itcast.service.admin;

import cn.itcast.domain.admin.AdminModel;

public interface IAdminService {

	public AdminModel login(String username,String password);
}
