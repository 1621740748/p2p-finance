package cn.itcast.service.charges;

public interface IChargeService {
	public void recharge(String bankCardNum, double money,int userId);
}
