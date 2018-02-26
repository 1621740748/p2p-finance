package cn.itcast.service.charges.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.userAccount.IUserAccountDAO;
import cn.itcast.service.charges.IChargeService;
import cn.itcast.service.userAccount.IUserAccountService;
import cn.itcast.utils.HttpClientUtil;

@Service
@Transactional
public class ChargeServiceImpl implements IChargeService {
	@Autowired	
	private IUserAccountDAO userAccountDao;

	// 完成转账操作
	public void recharge(String bankCardNum, double money,int userId) {

		// 1.访问webservice服务完成银行转账
		// 调用httpClient工具来完成访问Mybank资源

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("bankCardNum", bankCardNum);
		map.put("money", money);

		String result = HttpClientUtil.visitWebService(map, "bank_url");

		if (Boolean.parseBoolean(result)) {

			// 2.根据银行转账结果来完成自己的平台帐户信息改变
			//修改userAccount中的total blance
			userAccountDao.charge(money,userId);
		} else {
			// 金额不足，转账失败
		}
	}
}
