package cn.itcast.service.bank;

import java.util.List;

import cn.itcast.domain.bankCardInfo.Bank;

public interface IBankService {

	List<Bank> findAll();

}
