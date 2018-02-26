package cn.itcast.server.impl;

import cn.itcast.server.Bank;

public class BankImpl implements Bank {

	@Override
	public boolean recharge(String bankCardId, double money) {
		System.out.println(111);
		return false;
	}

}
