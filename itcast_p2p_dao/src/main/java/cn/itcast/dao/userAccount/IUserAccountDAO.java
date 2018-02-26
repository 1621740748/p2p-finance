package cn.itcast.dao.userAccount;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.domain.userAccount.UserAccountModel;

public interface IUserAccountDAO extends JpaRepository<UserAccountModel, Integer> {

	public UserAccountModel findByUserId(int userId);

	@Modifying
	@Query("update UserAccountModel uam set uam.total=uam.total+?1,uam.balance=uam.balance+?1 where uam.userId=?2")
	public void charge(double money, int userId);
}
