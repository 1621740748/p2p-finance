package cn.itcast.domain.debtorrecord;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


//债权还款记录实体类

@Entity
@Table(name="t_debtor_record")
public class DebtorModel {
	
	@Id
	@GeneratedValue()
	@Column(name="T_ID")
	private int id;						//主键
	
	@Column(name="T_CLAIMS_ID")
	private int claimsId;				//债权ID
	
	@Column(name="T_RECEIVABLE_DATE")
	private Date receivableDate;		//应还日期
	
	@Column(name="T_RECEIVEABLE_MONEY")
	private Double receivableMoney;		//应还金额
	
	@Column(name="T_CURRENT_TERM")
	private int currentTerm;			//当前还款期
	
	@Column(name="T_RECORD_DATE")
	private Date recordDate;			//记录日期
	
	@Column(name="T_ISRETURNED")
	private int isReturned;				//是否还款
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getClaimsId() {
		return claimsId;
	}
	public void setClaimsId(int claimsId) {
		this.claimsId = claimsId;
	}
	public Date getReceivableDate() {
		return receivableDate;
	}
	public void setReceivableDate(Date receivableDate) {
		this.receivableDate = receivableDate;
	}
	public Double getReceivableMoney() {
		return receivableMoney;
	}
	public void setReceivableMoney(Double receivableMoney) {
		this.receivableMoney = receivableMoney;
	}
	
	public int getCurrentTerm() {
		return currentTerm;
	}
	public void setCurrentTerm(int currentTerm) {
		this.currentTerm = currentTerm;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public int getIsReturned() {
		return isReturned;
	}
	public void setIsReturned(int isReturned) {
		this.isReturned = isReturned;
	}
	

}
