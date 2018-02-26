package cn.itcast.service.weightRule.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.admin.IWeigthRuleDAO;
import cn.itcast.domain.admin.WeigthRule;
import cn.itcast.service.weightRule.IWeigthRuleService;

@Service
public class WeigthRuleServiceImpl implements IWeigthRuleService {

	@Autowired
	private IWeigthRuleDAO weightRuleDao;
	
	@Override
	public WeigthRule findByWeigthType(int i) {
		return weightRuleDao.findByWeigthType(i);
	}

}
