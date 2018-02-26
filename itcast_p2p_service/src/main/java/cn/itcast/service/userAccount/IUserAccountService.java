package cn.itcast.service.userAccount;

import cn.itcast.domain.userAccount.UserAccountModel;

public interface IUserAccountService {

	void add(int id);

	UserAccountModel findByUserId(int userId);

}
