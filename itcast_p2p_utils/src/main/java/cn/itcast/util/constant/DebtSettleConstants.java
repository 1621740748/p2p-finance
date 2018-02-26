package cn.itcast.util.constant;

import org.apache.commons.lang.StringUtils;

/**
 * 类描述：债权清算常量类
 */
public class DebtSettleConstants {
	
	/**
	 * 173 该类型的产品不能进行提前结清
	 */
	public static final Integer DEBT_DONT_AGREE_BEFORE_SETTLE = 173;
	/**
	 * 债权管理 债券状态 120
	 */
	public static final Integer DEBT__BOND_TYPE = 120;
	/**
	 * 债权管理 债权匹配状态 121
	 */
	public static final Integer DEBT_MATCHING_TYPE = 121;
	/**
	 * 债权管理 还款状态 122
	 */
	public static final Integer DEBT_REPAY_TYPE = 122;
	
	
	
	/**
	 * 债权状态 未审核 11301
	 */
	public static final Integer DEBT_UNCHECKED = 11301;
	/**
	 * 债权状态 已审核11302
	 */
	public static final Integer DEBT_CHECKED = 11302;
	/**
	 *  债权状态 正常还款11303
	 */
	public static final Integer DEBT_COMMON_PAY =11303;
	/**
	 *  债权状态 已结清11304
	 */
	public static final Integer DEBT_PAY_CLOSED = 11304;
	/**
	 * 债权状态 提前结清11305
	 */
	public static final Integer DEBT_PAY_BEFORE = 11305;
	/**
	 * 债权状态 结算失败11306
	 */
	public static final Integer DEBT_PAY_FAILURE = 11306;
	
	
	

	/**
	 * 债权匹配状态 部分匹配11401
	 */
	public static final Integer DEBE_PART_MATCH = 11401;
	/**
	 * 债权匹配状态 完全匹配11402
	 */
	public static final Integer DEBT_ALL_MATCH = 11402;
	/**
	 * 债权匹配状态 未匹配11403
	 */
	public static final Integer DEBT_UNMATCH = 11403;
	/**
	 * 债权匹配状态 正在匹配11404
	 */
	public static final Integer DEBT_MATCHING = 11404;
	
	
	

	/**
	 * 还款状态 本期已还11501
	 */
	public static final Integer DEBT_THIS_PERIOD_PAYED = 11501;
	/**
	 * 还款状态 未到期 11502
	 */
	public static final Integer DEBT_UNUNTIL_PERIOD = 11502;
	
	
	
	/**
	 * 回款 106
	 */
	public static final Integer DEBT_BACK_PAY = 106;
	/**
	 * 回款 交易成功 10601
	 */
	public static final Integer DEBT_BACK_PAY_SUCCESS = 10601;
	
	
	
	/**
	 * 放款107
	 */
	public static final Integer DEBT_FREE_PAY = 107;
	/**
	 * 放款 交易成功10701
	 */
	public static final Integer DEBT_FREE_PAY_SUCCESS = 10701;
	
	
	
	/**
	 * 还款108
	 */
	public static final Integer DEBT_RETURN_PAY = 108;
	/**
	 * 还款----交易成功 10801
	 */
	public static final Integer DEBT_RETURN_PAY_SUCCESS = 10801;
	/**
	 * 还款----交易失败 1080
	 */
	public static final Integer DEBT_RETURN_PAY_FAILURE = 10800;
	
	/**
	 * 到期赎回(投资赎回)
	 */
	public static final Integer REDEMPTION = 147 ;
	/**
	 * 到期赎回(投资赎回)---成功
	 */
	public static final Integer REDEMPTION_SUCCEED = 12401;
	
	
	/**
	 * 系统复投146
	 */
	public static final Integer DETB_SYS_REINVEST = 146;
	/**
	 * 系统复投---交易成功 12301
	 */
	public static final Integer DETB_SYS_REINVEST_SUCCESS = 12301;
	/**
	 * 系统复投--交易失败12300
	 * */
	public static final Integer DETB_SYS_REINVEST_FAILURE = 12300;
	/**
	 * 系统复投--交易申请中12302
	 */
	public static final Integer DETB_SYS_REINVEST_APPLYING = 12302;
	
	
	
	/**
	 * 老系统产品 月定投151
	 */
	public static final Integer DEBT_MONTH_DEFINED_INVEST = 151;
	/**
	 * 老系统产品 149散标
	 */
	public static final Integer DEBT_SANBIAO = 149;
	/**
	 * 老系统产品 150 E计划
	 */
	public static final Integer DEBT_E_PLAN = 150;
	
	
	
	/**
	 * 冻结类型 114
	 */
	public static final Integer DEBT_FROZEN = 114;
	/**
	 * 冻结状态 12601
	 */
	public static final Integer DEBT_FROZENSTATE = 12601;
	/**
	 * 回款再投资 125
	 */
	public static final Integer REPEAT_FROZEN_MONEY =125;
	/**
	 * 待匹配 10901
	 */
	public static final Integer WAIT_MATCH_MONEY =10901;
	
   /**
    * 清算结果--成功 11201
    **/
	public static final int SETTLE_SUCCESS = 12201;
	/**
	 * 清算结果--失败 12200
	 */
	public static final int SETTLE_FAIL  = 12200;
	/**
	 * 清算结果--执行中 12299
	 */
	public static final int SETTLE_EXEC  = 12299;
	/**
	 * 清算结果--初始化 12289
	 */
	public static final int SETTLE_WAIT  = 12289;
	/**
	 * 清算结果--执行成功
	 */
	public static final String SETTLE_SUCCESS_NAME  = "定时任务执行成功   ";
	/**
	 * 清算结果--执行失败
	 */
	public static final String SETTLE_FAIL_NAME  = "定时任务执行失败   ";
	/**
	 * 清算结果--执行中
	 */
	public static final String SETTLE_EXEC_NAME  = "定时任务执行中   ";
	/**
	 * 清算结果--待执行
	 */
	public static final String SETTLE_WAIT_NAME  = "定时任务待执行  ";
	/**
	 * 清算结果--前台可以点击标记
	 */
	public static final int SETTLE_FLAG  = 1;//1为可以点击
	
	/**
	 * 清算结果--前台不可以点击标记
	 */
	public static final int SETTLE_NOTFLAG  = 0;//0为不可以点击
	
	/**
	 * 系统复投成功后冻结金额
	 */
	public static final double ZERO =0.00;
	
	/**
	 * 资金日结算时间
	 */
	public static final String DEBT_SETTLE_TIME = "0:00";

	/**
	 * 撮合管理 提前结清 127
	 */
	public static final Integer DEBT_EARLY = 127;
	/**
	 * 撮合管理 提前结清 126
	 */
	public static final Integer DEBT_RXPIRT = 126;
	
	
	/**
	 * 收 11001 
	 */
	public static final Integer DEBT_RECEIVE = 11001;
	/**
	 * 付 11002  
	 */
	public static final Integer DEBT_PAY = 11002;
	
	/**
	 * 最小复投金额 100
	 */
	public static final String MIN_REPEAT = "100";
	
	/**
	 * 没有可用结果 2
	 */
	public static final int NULL_RESULT =2;

	/**
	 * 前端状态码
	 * 当前标的为  的债权待匹配队列有正在匹配的数据
	 */
	public static final String ISLOCK ="169";
	/**
	 * 前端状态码
	 * 当前标的为  的债权待匹配队列数据为空
	 */
	public static final String NULL_BONDQUEUE  ="170";
	
	
	
	/**
	 * 渠道厂商 新数网络（dsp）
	 */
	public static final String DSP_WISEMEDIA   ="731710";
	
	/**
	 * 渠道厂商   聚享游（cpa）
	 */
	public static final String DSP_POLYSNJOYSWIM   ="834437";
	
	/**
	 * 渠道厂商   是否已发送  1代表已发送
	 */
	public static final int SEND =1;
	
	/**
	 * 渠道厂商   是否已发送  0代表未发送
	 */
	public static final int NOT_SEND =0;
	
	/**
	 * 渠道厂商 快乐赚(cpa)
	 */
	public static final String DSP_HAPPYTOEARN   ="187647";
	
	public static final String getStateMessage(String state){
		if(StringUtils.isEmpty(state)){
			return "";
		}
		if(state.equals("1")){
			return "Success,绑定成功";
		}else if(state.equals("0")){
			return "Failure,绑定失败";
		}else if(state.equals("-1")){
			return "idCode不能为空";
		}else if(state.equals("-2")){
			return "idName不能为空";
		}else if(state.equals("-3")){
			return "adID不是纯数字,adID必须为纯数字";
		}else if(state.equals("-4")){
			return "leID不是纯数字,leID必须为纯数字";
		}else if(state.equals("-5")){
			return "sign加密结果错误";
		}else if(state.equals("-6")){
			return "annalID的测试范围在小于10000";
		}else if(state.equals("-7")){
			return "leID已绑定过了";
		}else if(state.equals("400000")){
			return "SUCCEED";
		}else{
			return "FAIL";
		}
	}
	
	
	/**
	 * 投资状态  已回收
	 */
	public static final int TOUZI_YIHUISHOU =10904;
}
