package cn.itcast.service.creditor.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.dao.admin.IWeigthRuleDAO;
import cn.itcast.dao.creditor.ICreditorDAO;
import cn.itcast.dao.creditor.ICreditorForSqlDAO;
import cn.itcast.dao.debtormodel.IDebtormodelDAO;
import cn.itcast.dao.waitMatchLoan.IWaitMatchLoanDAO;
import cn.itcast.domain.admin.WeigthRule;
import cn.itcast.domain.creditor.CreditorModel;
import cn.itcast.domain.debtorrecord.DebtorModel;
import cn.itcast.domain.po.waitmatchloan.WaitMatchLoanPO;
import cn.itcast.service.creditor.ICreditorService;
import cn.itcast.util.constant.DebtSettleConstants;
import cn.itcast.utils.CalcuDateUtil;
import cn.itcast.utils.ClaimsType;
import cn.itcast.utils.DateUtil;

@Service
@Transactional
public class CreditorServiceImpl implements ICreditorService {

	@Autowired
	private ICreditorDAO creditorDao;

	@Autowired
	private ICreditorForSqlDAO creditorForSqlDao;
	@Autowired
	private IWeigthRuleDAO weigthRuleDao;
	@Autowired
	private IWaitMatchLoanDAO waitMatchLoanDao;
	@Autowired
	private IDebtormodelDAO debtormodelDao;

	@Override
	public void addCreditor(CreditorModel creditor) {
		creditorDao.save(creditor);
	}

	@Override
	public void addMultiple(List<CreditorModel> cms) {
		creditorDao.save(cms);
	}

	@Override
	public List<CreditorModel> findCreditorByCondition(Map<String, Object> map) {
		return creditorForSqlDao.findCreditorByCondition(map);
	}

	@Override
	public List<Object[]> findCreditorBySum(Map<String, Object> map) {
		return creditorForSqlDao.findCreditorBySum(map);
	}

	@Override
	public void checkCreditor(String[] id) {
		for (int i = 0; i < id.length; i++) {
			// 查询债权信息
			CreditorModel cm = creditorDao.findOne(Integer.parseInt(id[i]));
			// 修改债权审核状态
			if (cm != null) {
				cm.setDebtStatus(ClaimsType.CHECKED);

				// 插入债权转让表信息
				WaitMatchLoanPO matchLoan = new WaitMatchLoanPO();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
				matchLoan.setTransferSerialNo("LSNO" + sdf.format(new Date()));
				matchLoan.setDebtId(Integer.valueOf(id[i]));
				matchLoan.setDebtMoney(BigDecimal.valueOf(cm.getAvailableMoney()));
				matchLoan.setDebtType(ClaimsType.NEW_BORROW);// 债权类型 :新借款
																// 129,到期赎回
																// 130,期限内回款 131
				matchLoan.setIsLocked(DebtSettleConstants.DEBT_UNMATCH);// 是否锁定:11404,锁定中(匹配中);11403,未锁定(未匹配)
				matchLoan.setTransferDate(new Timestamp(DateUtil.getLongDate().getTime()));
				matchLoan.setDebtOwner(1);
				matchLoan.setAuditStatus(DebtSettleConstants.DEBT_CHECKED);// 权状态
																			// 已审核

				// 债权权重值是从权重规则设置（T_WEIGHT_RULE）查询
				WeigthRule weigthRule = weigthRuleDao.findByWeigthType(ClaimsType.NEW_BORROW);
				matchLoan.setDebtWeight(weigthRule.getWeigthValue());// 债权权重值是从权重规则设置（T_WEIGHT_RULE）查询
				waitMatchLoanDao.save(matchLoan);

				// 插入债权还款记录
				DebtorModel dm = debtormodelDao.findOne(cm.getId());// 根据id查询
				if (dm == null) {
					// 说明刚审核过无还款记录，插入还款记录表
					int debtNumber = cm.getAvailablePeriod(); // 债权期数
					String debtNo = cm.getDebtNo(); // 债权编号
					System.out.println("编号=" + debtNo + "的摘取，可用期数=" + debtNumber);
					List<DebtorModel> debts = new ArrayList<DebtorModel>();
					for (int j = 0; j < debtNumber; j++) {
						DebtorModel debt = new DebtorModel();
						debt.setClaimsId(Integer.valueOf(id[i]));// 债权ID
						debt.setRecordDate(DateUtil.getLongDate());// 记录还款记录生成日期
						debt.setCurrentTerm(j + 1);// 当前期
						Date d1 = CalcuDateUtil.getReturnDate(Integer.parseInt(cm.getRepaymenDate()));// 计算下个月还款日期
						Date d2 = DateUtil.getDateNextMonth(d1, j);
						debt.setReceivableDate(d2);// 还款日期
						debt.setReceivableMoney(cm.getRepaymenMoney());// 还款金额
						debt.setIsReturned(DebtSettleConstants.DEBT_UNUNTIL_PERIOD);
						debts.add(debt);
					}
					debtormodelDao.save(debts);

				}
			}
		}

	}

}
