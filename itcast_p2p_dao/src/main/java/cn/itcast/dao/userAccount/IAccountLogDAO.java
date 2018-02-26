package cn.itcast.dao.userAccount;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.domain.admin.AccountLog;
import cn.itcast.domain.userAccount.UserAccountModel;

public interface IAccountLogDAO extends JpaRepository<AccountLog, Integer> {

	@Query("select fnmm.fFoundingWeight,um.username,pa.pSerialNo,pa.pProductName,al.aDate,pa.pDeadline,al.aCurrentPeriod,fnmm.fNotMatchedMoney,fnmm.fFoundingType "
			+ "from AccountLog al,ProductAccount pa,FundingNotMatchedModel fnmm,UserModel um "
			+ "where al.aUserId=um.id and al.pId=pa.pId and fnmm.fInvestRecordId=pa.pId and  fnmm.fIsLocked = 10901 And fnmm.fFoundingType in(124,125,126,127)")
	List<Object[]> findWaitMoney();

	@Query("select count(pa.pId),sum(fnmm.fNotMatchedMoney)  "
			+ "from AccountLog al,ProductAccount pa,FundingNotMatchedModel fnmm,UserModel um "
			+ "where al.aUserId=um.id and al.pId=pa.pId and fnmm.fInvestRecordId=pa.pId and  fnmm.fIsLocked = 10901 And fnmm.fFoundingType in(124,125,126,127)")
	List<Object[]> findWaitMoneySum();
}
