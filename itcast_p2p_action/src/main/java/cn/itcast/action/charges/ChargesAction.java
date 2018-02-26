package cn.itcast.action.charges;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;

import cn.itcast.action.common.BaseAction;
import cn.itcast.action.filter.GetHttpResponseHeader;
import cn.itcast.cache.BaseCacheService;
import cn.itcast.domain.admin.AccountLog;
import cn.itcast.domain.admin.ExpectedReturn;
import cn.itcast.domain.admin.FundingNotMatchedModel;
import cn.itcast.domain.admin.ProductAccount;
import cn.itcast.domain.admin.WeigthRule;
import cn.itcast.domain.bankCardInfo.BankCardInfo;
import cn.itcast.domain.product.Product;
import cn.itcast.domain.userAccount.UserAccountModel;
import cn.itcast.service.bankCardInfo.IBankCardInfoService;
import cn.itcast.service.charges.IChargeService;
import cn.itcast.service.expectedReturn.IExpectedReturnService;
import cn.itcast.service.product.IProductService;
import cn.itcast.service.productAccount.IProductAccountService;
import cn.itcast.service.userAccount.IUserAccountService;
import cn.itcast.service.weightRule.IWeigthRuleService;
import cn.itcast.utils.BigDecimalUtil;
import cn.itcast.utils.FundsFlowType;
import cn.itcast.utils.InvestStatus;
import cn.itcast.utils.InvestTradeType;
import cn.itcast.utils.RandomNumberUtil;
import cn.itcast.utils.Response;
import cn.itcast.utils.TimestampUtils;

@Namespace("/charges")
@Controller
@Scope("prototype")
public class ChargesAction extends BaseAction {

	@Autowired
	private BaseCacheService baseCacheService;

	@Autowired
	private IBankCardInfoService bankCardInfoService;

	@Autowired
	private IChargeService chargeService;

	@Autowired
	private IUserAccountService userAccountService;

	@Autowired
	private IProductService productService;

	@Autowired
	private IProductAccountService productAccountService;

	@Autowired
	private IWeigthRuleService weigthRuleService;

	@Autowired
	private IExpectedReturnService expectedReturnService;

	// 投资信息,分页查询
	@Action("ProductAccountBuying")
	public void ProductAccountBuying() {
		this.getResponse().setCharacterEncoding("utf-8");
		// 1.得到页码
		String currentPage = this.getRequest().getParameter("currentPage");
		int cp = Integer.parseInt(currentPage);
		int currentNum = 2;
		// 2.service
		Page<ProductAccount> page = productAccountService.pageQuery(cp - 1, currentNum);
		List<ProductAccount> list = page.getContent();
		long totalElements = page.getTotalElements();
		int totalPages = page.getTotalPages();
		// 3.响应浏览器
		try {
			this.getResponse().getWriter()
					.write(Response.build().setStatus("1").setData(list).setTotal(totalElements + "").toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 充值操作
	@Action("charge")
	public void charge() {
		// 1.得表请求参数
		String chargeMoney = this.getRequest().getParameter("chargeMoney");
		// 2.根据当前用户id，得到用户的绑定的银行卡号
		String token = GetHttpResponseHeader.getHeadersInfo(this.getRequest());
		Map<String, Object> hmap = baseCacheService.getHmap(token);
		int userId = (int) hmap.get("id");
		// 查询银行卡号
		BankCardInfo bci = bankCardInfoService.findByUserId(userId);
		String bankCardNum = bci.getBankCardNum();
		// 3.调用service完成充值操作
		chargeService.recharge(bankCardNum, Double.parseDouble(chargeMoney), userId);
	}

	// 理财产品购买
	@Action("addMayTake")
	public void addMayTake() {
		// 1.得到token
		String token = GetHttpResponseHeader.getHeadersInfo(this.getRequest());
		Map<String, Object> hmap = baseCacheService.getHmap(token);
		int userId = (int) hmap.get("id");
		String username = (String) hmap.get("userName");

		// 2.获取请求参数
		String pProductId = this.getRequest().getParameter("pProductId"); // 理财产品id
		String pAmount = this.getRequest().getParameter("pAmount"); // 投资金额
		String pDeadline = this.getRequest().getParameter("pDeadline"); // 期限
		String pExpectedAnnualIncome = this.getRequest().getParameter("pExpectedAnnualIncome"); // 年化收益
		String pMonthInterest = this.getRequest().getParameter("pMonthInterest"); // 月赢取利息
		String pMonthlyExtractInterest = this.getRequest().getParameter("pMonthlyExtractInterest"); // 每月提取利息

		// 总本息
		String endInvestTotalMoney = BigDecimalUtil.endInvestTotalMoney(pAmount, pDeadline, pExpectedAnnualIncome,
				pMonthlyExtractInterest);

		// 本次待收利息 =总本息-投资金额
		BigDecimal mayInterrestIncome = BigDecimalUtil.sub(endInvestTotalMoney, pAmount);

		// 3.封装用户帐户表信息
		// 先查询用户帐户表数据
		UserAccountModel userAccount = userAccountService.findByUserId(userId);
		// a. 修改帐户中的余额 现余额=原余额-投资金额
		BigDecimal _balance = BigDecimalUtil.sub(userAccount.getBalance(), Double.parseDouble(pAmount));
		// b. 总计待收本金 =原总计待收本金+投资金额
		BigDecimal _inverstmentW = BigDecimalUtil.add(userAccount.getInverstmentW(), Double.parseDouble(pAmount));
		// c. 总计待收利息 =原总计待收利息+本次待收利息
		BigDecimal _interestTotal = BigDecimalUtil.add(userAccount.getInterestTotal(),
				mayInterrestIncome.doubleValue());
		// d. 月取计划投资总额 原月取总额+投资金额
		BigDecimal _recyclingInterest = BigDecimalUtil.add(userAccount.getRecyclingInterest(),
				Double.parseDouble(pAmount));
		// e. 已投资总额 原投资总额+投资金额
		BigDecimal _inverstmentA = BigDecimalUtil.add(userAccount.getInverstmentA(), Double.parseDouble(pAmount));

		UserAccountModel uam = new UserAccountModel();
		uam.setBalance(_balance.doubleValue());
		uam.setInverstmentW(_inverstmentW.doubleValue());
		uam.setInterestTotal(_interestTotal.doubleValue());
		uam.setRecyclingInterest(_recyclingInterest.doubleValue());
		uam.setInverstmentA(_inverstmentA.doubleValue());
		uam.setId(userAccount.getId());

		// 4.ProductAccount表数据封装
		ProductAccount pa = new ProductAccount();

		// 它需要产品信息 ---从请求参数中可以获取产品id,从数据库中查询出产品信息
		Product product = productService.findById(Long.parseLong(pProductId));
		pa.setpProductName(product.getProductName());
		pa.setpProductId(product.getProId());
		// 它需要用户信息--token
		pa.setpUid((long) userId);
		pa.setUsername(username);
		// 本身信息
		// a. 开始时间 ---new Date()
		Date date = new Date();
		pa.setpBeginDate(date);
		// b. 结束时间 ---new Date()+投资期限
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, Integer.parseInt(pDeadline));
		pa.setpEndDate(c.getTime());
		// c. 投资编号—“TZNO”+随机id它是根据时间生成
		String randomNum = RandomNumberUtil.randomNumber(date);
		pa.setpSerialNo("TZNO" + randomNum);
		// d. 投资金额----请求参数
		pa.setpAmount(Double.parseDouble(pAmount));
		// e. 投资期限---请求参数
		pa.setpDeadline(Integer.parseInt(pDeadline));
		// f. 年化率-----请求参数
		pa.setpExpectedAnnualIncome(Double.parseDouble(pExpectedAnnualIncome));
		// g. 月利息-----请求参数
		pa.setpMonthInterest(Double.parseDouble(pMonthInterest));
		// h. 月提取利息----请求参数
		pa.setpMonthlyExtractInterest(Double.parseDouble(pMonthlyExtractInterest));
		// i. 可用余额-----在用户帐户表中有
		pa.setpAvailableBalance(_balance.doubleValue());
		// j. 到期收回总本金----在用户帐户表中有
		pa.setpEndInvestTotalMoney(_inverstmentW.doubleValue());
		// k. 待匹配状态---从匹配状态工具类中获取 InvestStatus
		pa.setpStatus(InvestStatus.WAIT_TO_MATCH);
		// l. 还有其它项
		pa.setaCurrentPeriod(1);

		// 5.交易流水
		AccountLog accountLog = new AccountLog();
		// 1.需要用户id
		accountLog.setaUserId(userId);
		// 2.主帐户id记录成用户id
		accountLog.setaMainAccountId(userId);
		// 3.需要投资记录的id----就是ProductAccount主键
		accountLog.setpId(pa.getpId());
		// 4.当前期----第一期
		accountLog.setaCurrentPeriod(1);
		// 5.收付-----从工具类中获取InvestTradeType. PAY
		accountLog.setaReceiveOrPay(InvestTradeType.PAY);
		// 6.交易流水号 LSNO+随机id
		accountLog.setaTransferSerialNo("LSNO" + randomNum);
		// 7.交易时间 new Date()
		accountLog.setaDate(date);
		// 8.交易类型 FundsFlowType. INVEST_TYPE
		accountLog.setaType(FundsFlowType.INVEST_TYPE);
		// 9.交易状态 FundsFlowType. INVEST_SUCCESS
		accountLog.setaTransferStatus(FundsFlowType.INVEST_SUCCESS);
		// 10.交易前金额 记录的是交易前的余额
		accountLog.setaBeforeTradingMoney(userAccount.getBalance());
		// 11.交易金额 投资的金额
		accountLog.setaAmount(Double.parseDouble(pAmount));
		// 12.交易后金额 记录的是交易后的余额
		accountLog.setaAfterTradingMoney(_balance.doubleValue());
		// 13.交易详情 月取操作+投资流水号
		accountLog.setaDescreption("月取计划TZNO" + randomNum);

		// 6.向待匹配资金表中插入数据
		FundingNotMatchedModel fnmm = new FundingNotMatchedModel();
		// 1.投资记录id---就是ProductAccount的id\
		//fnmm.setfInvestRecordId(pa.getpId());
		// 2.待匹配金额----就是投资金额
		fnmm.setfNotMatchedMoney(Double.parseDouble(pAmount));
		// 3.资金类型 124 新增投资
		fnmm.setfFoundingType(124);
		// 4.投资时间 new Date
		fnmm.setfCreateDate(date);
		// 5.权重
		WeigthRule wr = weigthRuleService.findByWeigthType(124);
		fnmm.setfFoundingWeight(wr.getWeigthValue());
		fnmm.setfIsLocked(FundsFlowType.FUND_NOT_LOCK);
		fnmm.setUserId(userId);

		// 7.操作
		productAccountService.addProductAccount(uam, pa, accountLog, fnmm);

		// 8.预期收益操作
		for (int i = 0; i < Integer.parseInt(pDeadline); i++) {
			ExpectedReturn er = new ExpectedReturn();
			// 封装数据
			// 1. 用户id
			er.setUserId(userId);
			// 2. 产品id
			er.setProductId((int) (product.getProId()));
			// 3. 投资记录id
			er.setInvestRcord(pa.getpId());
			// 4. 收益日期 当前月份+1
			er.setExpectedDate(TimestampUtils.nextMonth(date.getYear(), date.getMonth(), i));
			// 5. 收益金额、-----从请求参数中获取
			er.setExpectedMoney(Double.parseDouble(pMonthInterest));
			// 6. 创建日期 new Date()
			er.setCreateDate(date);
			expectedReturnService.add(er);
		}
		// 9.发送短信，发送邮件
		System.out.println("完成理财产品购买操作");
		// 10响应成功
		try {
			this.getResponse().getWriter().write(Response.build().setStatus("1").toJSON());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
