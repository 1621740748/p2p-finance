package cn.itcast.action.creditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.IOUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.action.common.BaseAction;
import cn.itcast.domain.creditor.CreditorModel;
import cn.itcast.domain.creditor.CreditorSumModel;
import cn.itcast.service.creditor.ICreditorService;
import cn.itcast.utils.ClaimsType;
import cn.itcast.utils.ConstantUtil;
import cn.itcast.utils.FileUtil;
import cn.itcast.utils.FrontStatusConstants;
import cn.itcast.utils.RandomNumberUtil;
import cn.itcast.utils.Response;
import cn.itcast.utils.excelUtil.DataFormatUtilInterface;
import cn.itcast.utils.excelUtil.ExcelDataFormatException;
import cn.itcast.utils.excelUtil.MatchupData;
import cn.itcast.utils.excelUtil.SimpleExcelUtil;

@Namespace("/creditor")
@Controller
@Scope("prototype")
public class CreditorAction extends BaseAction {

	@Autowired
	private ICreditorService creditorService;

	private String file;

	private String fileFileName;

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	//债券录入
	@Action("addCreditor")
	public void addCreditor() {
		CreditorModel creditor = new CreditorModel();
		try {

			if (StringUtils.isEmpty(creditor.getContractNo())) { // 判断借款Id（合同编号）是否为空
				getResponse().getWriter().write(Response.build().setStatus("38").toJSON());
				return;
			}
			if (StringUtils.isEmpty(creditor.getDebtorsName())) { // 判断债务人是否为空
				getResponse().getWriter().write(Response.build().setStatus("39").toJSON());
				return;
			}
			if (StringUtils.isEmpty(creditor.getDebtorsId())) { // 判断身份证号是否为空
				getResponse().getWriter().write(Response.build().setStatus("40").toJSON());
				return;
			}
			if (StringUtils.isEmpty(creditor.getLoanPurpose())) { // 判断借款用途是否为空
				getResponse().getWriter().write(Response.build().setStatus("41").toJSON());
				return;
			}
			if (StringUtils.isEmpty(creditor.getLoanType())) { // 判断借款类型是否为空
				getResponse().getWriter().write(Response.build().setStatus("42").toJSON());
				return;
			}
			if (StringUtils.isEmpty(creditor.getLoanPeriod() + "")) { // 判断原始期限（月）是否为空
				getResponse().getWriter().write(Response.build().setStatus("43").toJSON());
				return;
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			if (StringUtils.isEmpty(sdf.format(creditor.getLoanStartDate()))) { // 判断原始借款开始日期是否为空
				getResponse().getWriter().write(Response.build().setStatus("44").toJSON());
				return;
			}
			if (StringUtils.isEmpty(sdf.format(creditor.getLoanEndDate()))) { // 判断原始借款到期日期是否为空
				getResponse().getWriter().write(Response.build().setStatus("45").toJSON());
				return;
			}
			if (StringUtils.isEmpty(creditor.getRepaymentStyle() + "")) { // 判断还款方式是否为空
				getResponse().getWriter().write(Response.build().setStatus("46").toJSON());
				return;
			}
			if (StringUtils.isEmpty(creditor.getRepaymenDate())) { // 判断还款日是否为空
				getResponse().getWriter().write(Response.build().setStatus("47").toJSON());
				return;
			}
			if (StringUtils.isEmpty(creditor.getRepaymenMoney() + "")) { // 判断还款金额（元）是否为空
				getResponse().getWriter().write(Response.build().setStatus("48").toJSON());
				return;
			}
			if (StringUtils.isEmpty(creditor.getDebtMoney() + "")) { // 判断债权金额（元）是否为空
				getResponse().getWriter().write(Response.build().setStatus("49").toJSON());
				return;
			}
			if (StringUtils.isEmpty(creditor.getDebtMonthRate() + "")) { // 判断债权年化利率（%）是否为空
				getResponse().getWriter().write(Response.build().setStatus("50").toJSON());
				return;
			}
			if (StringUtils.isEmpty(creditor.getDebtTransferredMoney() + "")) { // 判断债权转入金额是否为空
				getResponse().getWriter().write(Response.build().setStatus("51").toJSON());
				return;
			}
			if (StringUtils.isEmpty(sdf.format(creditor.getDebtTransferredDate()))) { // 判断债权转入日期是否为空
				getResponse().getWriter().write(Response.build().setStatus("52").toJSON());
				return;
			}
			if (StringUtils.isEmpty(creditor.getDebtTransferredPeriod() + "")) { // 判断债权转入期限（月）是否为空
				getResponse().getWriter().write(Response.build().setStatus("53").toJSON());
				return;
			}
			if (StringUtils.isEmpty(sdf.format(creditor.getDebtRansferOutDate()))) { // 判断债权转出日期是否为空
				getResponse().getWriter().write(Response.build().setStatus("54").toJSON());
				return;
			}
			if (StringUtils.isEmpty(creditor.getCreditor())) { // 判断债权人是否为空
				getResponse().getWriter().write(Response.build().setStatus("55").toJSON());
				return;
			}
			// 录入债权信息
			creditor.setDebtNo("ZQNO" + RandomNumberUtil.randomNumber(new Date()));// 债权id
			creditor.setBorrowerId(1);// 贷款人id
			creditor.setDebtStatus(ClaimsType.UNCHECKDE); // 债权状态
			creditor.setMatchedMoney(0.00);// 匹配金额
			creditor.setMatchedStatus(ClaimsType.UNMATCH); // 匹配状态
			creditor.setDebtType(FrontStatusConstants.NULL_SELECT_OUTACCOUNT);// 标的类型
			creditor.setAvailablePeriod(creditor.getDebtTransferredPeriod());// 可用期限
			creditor.setAvailableMoney(creditor.getDebtTransferredMoney());// 可用金额
			creditorService.addCreditor(creditor);
			getResponse().getWriter().write(Response.build().setStatus("1").toJSON());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				getResponse().getWriter().write(Response.build().setStatus("0").toJSON());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Action("download")
	public void download() {
		// 1.获取文件路径
		String path = this.getRequest().getSession().getServletContext()
				.getRealPath("/WEB-INF/excelTemplate/ClaimsBatchImportTemplate.xlsx");
		FileInputStream fis = null;
		// 设置两个响应头,文件类型/下载方式
		String mimeType = this.getRequest().getSession().getServletContext()
				.getMimeType("ClaimsBatchImportTemplate.xlsx");
		this.getResponse().setHeader("content-type", mimeType);
		this.getResponse().setHeader("content-disposition", "attachment;filename=" + "ClaimsBatchImportTemplate.xlsx");
		try {
			// 2.获取输入流
			fis = new FileInputStream(path);
			OutputStream os = this.getResponse().getOutputStream();
			IOUtils.copy(fis, os);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Action("upload")
	public void upload() {
		// 1.完成文件上传操作
		// 上传文件保存到WEB-INF/upload下
		String path = this.getRequest().getSession().getServletContext().getRealPath("/WEB-INF/upload");
		String destName = new Date().getTime() + fileFileName;
		File destFile = new File(path, destName); // 目标文件
		InputStream is = null;
		try {
			FileUtil.copy(file, destFile);// 文件上传操作
			// 2.将文件中内容读取到封装成List<CreditorModel>
			is = new FileInputStream(path + "/" + destName);
			SimpleExcelUtil<CreditorModel> seu = new SimpleExcelUtil<CreditorModel>();

			List<CreditorModel> cms = seu.getDataFromExcle(is, "", 1, new MatchupData<CreditorModel>() {
				@Override
				public <T> T macthData(List<Object> data, int indexOfRow, DataFormatUtilInterface formatUtil) {
					CreditorModel creditor = new CreditorModel();
					if (data.get(0) != null) {
						creditor.setContractNo(data.get(0).toString()); // 债权合同编号
					} else {
						throw new ExcelDataFormatException("{" + 0 + "}");
					}
					creditor.setDebtorsName(data.get(1).toString().replaceAll("\\s{1,}", " "));// 债务人名称
					// 身份证号
					if (data.get(2) != null) {
						String data2 = data.get(2).toString().replaceAll("\\s{1,}", " ");
						String[] art = data2.split(" ");
						for (int i = 0; i < art.length; i++) {
							String str = art[i];
							// 暂无RegValidationUtil工具类,暂时放行
							/*
							 * if (!RegValidationUtil.validateIdCard(str)) {
							 * throw new ExcelDataFormatException("{" + 2 +
							 * "}"); }
							 */
						}
						creditor.setDebtorsId(data2);// 债务人身份证编号
					} else {
						throw new ExcelDataFormatException("{" + 2 + "}");
					}
					creditor.setLoanPurpose(data.get(3).toString()); // 借款用途
					creditor.setLoanType(data.get(4).toString());// 借款类型
					creditor.setLoanPeriod(formatUtil.formatToInt(data.get(5), 5)); // 原始期限月
					creditor.setLoanStartDate(formatUtil.format(data.get(6), 6));// 原始借款开始日期
					creditor.setLoanEndDate(formatUtil.format(data.get(7), 7));// 原始贷款到期日期
					// 还款方式
					if (ConstantUtil.EqualInstallmentsOfPrincipalAndInterest.equals(data.get(8))) {// 等额本息
						creditor.setRepaymentStyle(11601);
					} else if (ConstantUtil.MonthlyInterestAndPrincipalMaturity.equals(data.get(8))) {// 按月付息到月还本
						creditor.setRepaymentStyle(11602);
					} else if (ConstantUtil.ExpirationTimeRepayment.equals(data.get(8))) {// 到期一次性还款
						creditor.setRepaymentStyle(11603);
					} else {
						throw new ExcelDataFormatException("在单元格{" + 8 + "}类型不存在");
					}
					creditor.setRepaymenDate(data.get(9).toString());// 每月还款日
					creditor.setRepaymenMoney(formatUtil.formatToDouble(data.get(10), 10));// 月还款金额
					creditor.setDebtMoney(formatUtil.formatToDouble(data.get(11), 11));// 债权金额
					creditor.setDebtMonthRate(formatUtil.formatToDouble(data.get(12), 12));// 债权月利率
					creditor.setDebtTransferredMoney(formatUtil.formatToDouble(data.get(13), 13));// 债权转入金额
					creditor.setDebtTransferredPeriod(formatUtil.formatToInt(data.get(14), 14));// 债权转入期限
					creditor.setDebtRansferOutDate(formatUtil.format(data.get(15), 15));// 债权转出日期
					creditor.setCreditor(data.get(16).toString());// 债权人

					// 债权转入日期 原始开始日期+期限
					Date startDate = formatUtil.format(data.get(6), 6); // 原始开始日期
					int add = formatUtil.formatToInt(data.get(14), 14);// 期限
					Calendar c = Calendar.getInstance();
					c.setTime(startDate);
					c.add(Calendar.MONTH, add);
					creditor.setDebtTransferredDate(c.getTime());

					Date da = new Date();
					creditor.setDebtNo("ZQNO" + RandomNumberUtil.randomNumber(da));// 债权编号
					creditor.setMatchedMoney(Double.valueOf("0"));// 已匹配金额
					creditor.setDebtStatus(ClaimsType.UNCHECKDE); // 债权状态
					creditor.setMatchedStatus(ClaimsType.UNMATCH);// 债权匹配状态
					creditor.setBorrowerId(1); // 借款人id
					creditor.setDebtType(FrontStatusConstants.NULL_SELECT_OUTACCOUNT); // 标的类型
					creditor.setAvailablePeriod(creditor.getDebtTransferredPeriod());// 可用期限
					creditor.setAvailableMoney(creditor.getDebtTransferredMoney());// 可用金额
					return (T) creditor;

				}
			});
			// 3.调用service完成批量导入操作
			creditorService.addMultiple(cms);
			this.getResponse().getWriter().write(Response.build().setStatus("1").toJSON());
			return;
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			this.getResponse().getWriter().write(Response.build().setStatus("0").toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	// 查询债券信息,多条件
	@Action("getCreditorlist")
	public void getCreditorlist() {
		this.getResponse().setCharacterEncoding("utf-8");
		// dDebtNo=&dContractNo=&dDebtTransferredDateStart=&dDebtTransferredDateEnd=&dDebtStatus=&dMatchedStatus=&offsetnum=1
		// 1.获取请求参数
		String dDebtNo = this.getRequest().getParameter("dDebtNo"); // 标的编号
																	// --债权的编号
		String dContractNo = this.getRequest().getParameter("dContractNo"); // 借款的id
																			// ---全同编号
		String dDebtTransferredDateStart = this.getRequest().getParameter("dDebtTransferredDateStart"); // 债权转入日期
																										// 开始
		String dDebtTransferredDateEnd = this.getRequest().getParameter("dDebtTransferredDateEnd"); // 债权转入日期
																									// 结束
		String dDebtStatus = this.getRequest().getParameter("dDebtStatus"); // 债权的状态
		String dMatchedStatus = this.getRequest().getParameter("dMatchedStatus"); // 债权的匹配状态
		String offsetnum = this.getRequest().getParameter("offsetnum"); // 页码
		// 2.处理请求参数--将请求参数类型处理并封装到Map中，后续调用service时只需要将map传递就可以。
		Map<String, Object> map = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(dDebtNo)) {
			map.put("dDebtNo", dDebtNo);
		}
		if (StringUtils.isNotBlank(dContractNo)) {
			map.put("dContractNo", dContractNo);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if (StringUtils.isNotBlank(dDebtTransferredDateStart)) {
			try {
				map.put("dDebtTransferredDateStart", sdf.parse(dDebtTransferredDateStart));
			} catch (ParseException e) {
				try {
					this.getResponse().getWriter()
							.write(Response.build().setStatus(FrontStatusConstants.PARAM_VALIDATE_FAILED).toJSON());
					return;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		if (StringUtils.isNotBlank(dDebtTransferredDateEnd)) {
			try {
				map.put("dDebtTransferredDateEnd", sdf.parse(dDebtTransferredDateEnd));
			} catch (ParseException e) {
				try {
					this.getResponse().getWriter()
							.write(Response.build().setStatus(FrontStatusConstants.PARAM_VALIDATE_FAILED).toJSON());
					return;
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

		if (StringUtils.isNotBlank(dDebtStatus)) {
			map.put("dDebtStatus", Integer.parseInt(dDebtStatus));
		}
		if (StringUtils.isNotBlank(dMatchedStatus)) {
			map.put("dMatchedStatus", Integer.parseInt(dMatchedStatus));
		}
		// 处理分页信息
		int pageNum = Integer.parseInt(offsetnum); // 页码
		int currentNum = 10;// 每页条数
		int startIndex = (pageNum - 1) * currentNum;
		map.put("currentNum", currentNum);
		map.put("startIndex", startIndex);

		// 3.调用service查询债权信息
		// 3.1查询债权信息--多条件
		List<CreditorModel> cms = creditorService.findCreditorByCondition(map);

		// 注意:需要将债权状态及债权的匹配状态处理
		// <option value="">全部</option>
		// <option value="11301">未审核</option>
		// <option value="11302">已审核</option>
		// <option value="11303">正常还款</option>
		// <option value="11304">已结清</option>
		// <option value="11305">提前结清</option>
		// <option value="11306">结算失败</option>
		for (CreditorModel cm : cms) {
			// 处理债权状态
			if (cm.getDebtStatus() == 11301) {
				cm.setDebtStatusDesc("未审核");
			}
			if (cm.getDebtStatus() == 11302) {
				cm.setDebtStatusDesc("已审核");
			}
			if (cm.getDebtStatus() == 11303) {
				cm.setDebtStatusDesc("正常还款");
			}
			if (cm.getDebtStatus() == 11304) {
				cm.setDebtStatusDesc("已结清");
			}
			if (cm.getDebtStatus() == 11305) {
				cm.setDebtStatusDesc("提前结清");
			}
			if (cm.getDebtStatus() == 11306) {
				cm.setDebtStatusDesc("结算失败");
			}

			// 处理债权匹配状态
			// <option value="11401">部分匹配</option>
			// <option value="11402">完全匹配</option>
			// <option value="11403">未匹配</option>

			if (cm.getMatchedStatus() == 11401) {
				cm.setMatchedStatusDesc("部分匹配");
			}
			if (cm.getMatchedStatus() == 11402) {
				cm.setMatchedStatusDesc("完全匹配");
			}
			if (cm.getMatchedStatus() == 11403) {
				cm.setMatchedStatusDesc("未匹配");
			}

		}

		// 3.2查询债权的统计信息
		List<Object[]> cmsSum = creditorService.findCreditorBySum(map);
		// 将查询的结果封装到CreditorSumModel对象中
		CreditorSumModel csm = new CreditorSumModel();
		csm.setdIdCount(Integer.parseInt(cmsSum.get(0)[0].toString()));
		csm.setdDebtMoneySum((Double.parseDouble(cmsSum.get(0)[1].toString())));
		csm.setdAvailableMoneySum(Double.parseDouble(cmsSum.get(0)[2].toString()));

		List<CreditorSumModel> csms = new ArrayList<CreditorSumModel>();
		csms.add(csm);

		// 4.将查询结果响应到浏览器
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("date", cms);
		data.put("datasum", csms);
		try {
			this.getResponse().getWriter().write(Response.build().setStatus("1").setData(data).toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//债券审核
	@Action("checkCreditor")
	public void checkCreditor(){
		//1.得到请求参数
		String ids = this.getRequest().getParameter("ids");
		String[] id = ids.split(",");
		//2.调用service处理
		creditorService.checkCreditor(id);
		//3.响应状态
		try {
			this.getResponse().getWriter().write(Response.build().setStatus("1").toJSON());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
