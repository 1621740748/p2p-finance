package cn.itcast.service.bank.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.bank.IBankDAO;
import cn.itcast.domain.bankCardInfo.Bank;
import cn.itcast.service.bank.IBankService;

@Service
public class BankServiceImpl implements IBankService {

	@Autowired
	private IBankDAO bankDao;

	@Override
	public List<Bank> findAll() {
		return bankDao.findAll();
	}
}
