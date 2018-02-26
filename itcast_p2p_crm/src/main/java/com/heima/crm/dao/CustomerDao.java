package com.heima.crm.dao;

import java.util.List;

import com.heima.crm.domain.Customer;

public interface CustomerDao {
	// 获取定区未关联的客户信息
	public List<Customer> getNoAssociations();

	// 获取指定定区绑定客户信息
	public List<Customer> getInUserAssociations(String decidezone_id);

	// 定区绑定客户
	public void assignedCustomerToDecidedZone(String customer_id, String decidedZone_id);

	// 取消定区关联所有客户
	public void cancleDecidedZoneCustomers(String decidedZone_id);

	public Customer findCustomerByTelephone(String telephone);

	public Customer save(Customer customer);

	public void updateadressbyid(String customerid, String address);

	public Customer findCustomerByAddress(String address);

	public void setDecidedzoneNull(String customerid);

}
