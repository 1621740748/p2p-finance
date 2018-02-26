package cn.itcast.dao.bankCardInfo;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.domain.bankCardInfo.BankCardInfo;

public interface IBankCardInfoDAO extends JpaRepository<BankCardInfo, Integer>{

	BankCardInfo findByUserId(Integer userId);

}
