package cn.itcast.service.expectedReturn.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.expectedReturn.IExpectedReturnDAO;
import cn.itcast.domain.admin.ExpectedReturn;
import cn.itcast.service.expectedReturn.IExpectedReturnService;

@Service
@Transactional
public class ExpectedReturnServiceImpl implements IExpectedReturnService {

	@Autowired
	private IExpectedReturnDAO expectedReturnDao; 
	
	@Override
	public void add(ExpectedReturn er) {
		expectedReturnDao.save(er);
	}

}
