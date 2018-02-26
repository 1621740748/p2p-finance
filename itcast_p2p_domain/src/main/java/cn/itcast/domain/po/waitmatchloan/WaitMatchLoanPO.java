package cn.itcast.domain.po.waitmatchloan;

import java.math.BigDecimal;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="t_claims_transfer")
public class WaitMatchLoanPO {

	public WaitMatchLoanPO() {}
	
	public WaitMatchLoanPO(int id, int debtId, String transferSerialNo,
			int debtOwner, int debtType, Timestamp transferDate,
			int debtWeight, BigDecimal debtMoney, int auditStatus,int isLocked) {
		super();
		this.id = id;
		this.debtId = debtId;
		this.transferSerialNo = transferSerialNo;
		this.debtOwner = debtOwner;
		this.debtType = debtType;
		this.transferDate = transferDate;
		this.debtWeight = debtWeight;
		this.debtMoney = debtMoney;
		this.auditStatus = auditStatus;
		this.isLocked = isLocked;
	}

	public WaitMatchLoanPO(WaitMatchLoanPO loan) {
		super();
		this.id = loan.getId();
		this.debtId = loan.getDebtId();
		this.transferSerialNo = loan.getTransferSerialNo();
		this.debtOwner = loan.getDebtOwner();
		this.debtType = loan.getDebtType();
		this.transferDate = loan.getTransferDate();
		this.debtWeight = loan.getDebtWeight();
		this.debtMoney = loan.getDebtMoney();
		this.auditStatus = loan.getAuditStatus();
		this.isLocked = loan.getIsLocked();
	}
	
	@Id
	@GeneratedValue()
	@Column(name="c_id", nullable=false)
	private int id;//主键
	
	@Column(name="c_claims_id")
	private int debtId = 0;//债权id
	
	@Column(name="c_transfer_serial_no")
	private String transferSerialNo = "";//转让流水号
	
	@Column(name="c_user_id")
	private int debtOwner = 0;//债权持有人
	
	@Column(name="c_claims_type")
	private int debtType = 0;//债权类型 :新借款	129,到期赎回	130,期限内回款	131
	
	@Column(name="c_transfer_date")
	private Timestamp transferDate = new Timestamp(0);//转让时间
	
	@Column(name="c_claims_weight")
	private int debtWeight = 0;//债权权重
	
	@Column(name="c_transfer_money")
	private BigDecimal debtMoney = new BigDecimal(0);//债权金额 
	
	@Column(name="c_audit_status")
	private int auditStatus = 0;//审核状态
	
	@Column(name="c_is_locked")
	private int isLocked = 0;//是否锁定:11404,锁定中(匹配中);11403,未锁定(未匹配)
	
	/*@Transient
	private Map<String, String> extendData;//扩展属性
*/	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDebtId() {
		return debtId;
	}
	public void setDebtId(int debtId) {
		this.debtId = debtId;
	}
	public String getTransferSerialNo() {
		return transferSerialNo;
	}
	public void setTransferSerialNo(String transferSerialNo) {
		this.transferSerialNo = transferSerialNo;
	}
	public int getDebtOwner() {
		return debtOwner;
	}
	public void setDebtOwner(int debtOwner) {
		this.debtOwner = debtOwner;
	}
	public int getDebtType() {
		return debtType;
	}
	public void setDebtType(int debtType) {
		this.debtType = debtType;
	}
	public Timestamp getTransferDate() {
		return transferDate;
	}
	public void setTransferDate(Timestamp transferDate) {
		this.transferDate = transferDate;
	}
	public int getDebtWeight() {
		return debtWeight;
	}
	public void setDebtWeight(int debtWeight) {
		this.debtWeight = debtWeight;
	}
	
	public BigDecimal getDebtMoney() {
		if(null != debtMoney){
			return debtMoney;
		}else{
			return new BigDecimal(0);
		}
	}
	
	public void setDebtMoney(BigDecimal debtMoney) {
		this.debtMoney = debtMoney;
	}
	public int getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(int auditStatus) {
		this.auditStatus = auditStatus;
	}

	public int getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(int isLocked) {
		this.isLocked = isLocked;
	}

	/*public Map<String, String> getExtendData() {
		return extendData;
	}

	public void setExtendData(Map<String, String> extendData) {
		this.extendData = extendData;
	}*/
	
/*	
 * protected String thisInstanceToJson(){
		return JSONObject.toJSONString(this);
	}
	*/
	
	@Override
	public String toString() {
		return "WaitMatchLoanPO [id=" + id + ", debtId=" + debtId
				+ ", debtOwner=" + debtOwner + ", debtType=" + debtType
				+ ", transferDate=" + transferDate + ", debtWeight="
				+ debtWeight + ", debtMoney=" + debtMoney + ", isLocked="
				+ isLocked + "]";
	}
	
}
