package cn.itcast.service.productAccount.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.fundingNotMatched.IFundingNotMatchedDAO;
import cn.itcast.dao.product.IProductAccountDAO;
import cn.itcast.dao.userAccount.IAccountLogDAO;
import cn.itcast.dao.userAccount.IUserAccountDAO;
import cn.itcast.domain.admin.AccountLog;
import cn.itcast.domain.admin.FundingNotMatchedModel;
import cn.itcast.domain.admin.ProductAccount;
import cn.itcast.domain.userAccount.UserAccountModel;
import cn.itcast.service.productAccount.IProductAccountService;

@Service
@Transactional
public class ProductAccountServiceImpl implements IProductAccountService {

	@Autowired
	private IProductAccountDAO productAccountDao;
	@Autowired
	private IAccountLogDAO accountLogDao;
	@Autowired
	private IUserAccountDAO userAccountModelDao;
	@Autowired
	private IFundingNotMatchedDAO fundingNotMatchedDao;

	@Override
	public void addProductAccount(UserAccountModel uam, ProductAccount pa, AccountLog accountLog,
			FundingNotMatchedModel fnmm) {
		productAccountDao.saveAndFlush(pa);
		accountLog.setpId(pa.getpId());
		accountLogDao.save(accountLog);
		userAccountModelDao.save(uam);
		fnmm.setfInvestRecordId(pa.getpId());
		fundingNotMatchedDao.save(fnmm);
	}

	@Override
	public Page<ProductAccount> pageQuery(int cp, int currentNum) {
		Pageable pageable = new PageRequest(cp, currentNum);
		Page<ProductAccount> page = productAccountDao.findAll(pageable);
		return page;
	}

}
