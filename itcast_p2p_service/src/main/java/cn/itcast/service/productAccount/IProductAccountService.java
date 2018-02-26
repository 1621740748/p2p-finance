package cn.itcast.service.productAccount;

import org.springframework.data.domain.Page;

import cn.itcast.domain.admin.AccountLog;
import cn.itcast.domain.admin.FundingNotMatchedModel;
import cn.itcast.domain.admin.ProductAccount;
import cn.itcast.domain.userAccount.UserAccountModel;

public interface IProductAccountService {

	void addProductAccount(UserAccountModel uam, ProductAccount pa, AccountLog accountLog, FundingNotMatchedModel fnmm);

	Page<ProductAccount> pageQuery(int cp, int currentNum);

}
