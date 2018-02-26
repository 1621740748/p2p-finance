package cn.itcast.service.bankCardInfo;

import cn.itcast.domain.bankCardInfo.BankCardInfo;

public interface IBankCardInfoService {

	BankCardInfo findByUserId(Integer userId);

	void addBankCardInfo(BankCardInfo bci);

}
