package cn.itcast.service.accountLog.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.userAccount.IAccountLogDAO;
import cn.itcast.domain.productAcount.WaitMatchMoneyModel;
import cn.itcast.service.accountLog.IAccountLogService;

@Service
public class AccountLogServiceImpl implements IAccountLogService {

	@Autowired
	private IAccountLogDAO accountLogDao;

	// 查询待匹配资金队列信息
	@Override
	public List<WaitMatchMoneyModel> findWaitMoney() {
		// 1.调用dao查询待匹配资金队列信息
		List<Object[]> waitMoney = accountLogDao.findWaitMoney();
		// 2.将数据List<Object[]>封装成List<WaitMatchMoneyModel>
		List<WaitMatchMoneyModel> wmmms = null;
		if (waitMoney != null && waitMoney.size() > 0) {
			wmmms = new ArrayList<WaitMatchMoneyModel>();

			for (Object[] obj : waitMoney) {
				// 将WaitMatchMoneyModel对象的属性设置成obj数组中对应的值
				WaitMatchMoneyModel wmmm = new WaitMatchMoneyModel();
				// 封装数据
				wmmm.setFundWeight((Integer) obj[0]);
				wmmm.setUserName((String) obj[1]);
				wmmm.setpSerialNo((String) obj[2]);
				wmmm.setProductName((String) obj[3]);
				wmmm.setDate((Date) obj[4]);
				wmmm.setDeadline((Integer) obj[5]);
				wmmm.setCurrentMonth((int) obj[6]);
				wmmm.setAmountWait((Double) obj[7]);
				wmmm.setInvestType((Integer) obj[8]); // 124 125 126 127
				switch ((Integer) obj[8]) {
				case 124:
					wmmm.setInvestTypeDescrible("新增投资");
					break;
				case 125:
					wmmm.setInvestTypeDescrible("回款再投资");
					break;
				case 126:
					wmmm.setInvestTypeDescrible("到期结清");
					break;
				case 127:
					wmmm.setInvestTypeDescrible("提前结清");
					break;
				}
				wmmms.add(wmmm);
			}

		}

		return wmmms;
	}

	// 查询待匹配资金队列统计信息
	@Override
	public List<WaitMatchMoneyModel> findWaitMoneySum() {
		// 1.调用dao查询统计信息

		List<Object[]> objs = accountLogDao.findWaitMoneySum();

		// 2.将List<Object[]>转换成List<WaitMatchMoneyModel>
		List<WaitMatchMoneyModel> wmmmSum = null;
		if (objs != null && objs.size() > 0) {
			wmmmSum = new ArrayList<WaitMatchMoneyModel>();

			WaitMatchMoneyModel wmmm = new WaitMatchMoneyModel();

			wmmm.setCount(((Long) objs.get(0)[0]).intValue());
			wmmm.setSum((Double) objs.get(0)[1]);

			wmmmSum.add(wmmm);

		}

		return wmmmSum;
	}

}
