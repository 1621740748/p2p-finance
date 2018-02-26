package com.heima.crm.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.heima.crm.dao.CustomerDao;
import com.heima.crm.domain.Customer;
import com.heima.crm.service.CustomerService;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao customerDao;

	public List<Customer> getNoAssociations() {
		// TODO Auto-generated method stub
		return customerDao.getNoAssociations();
	}

	public List<Customer> getInUserAssociations(String decidezone_id) {
		// TODO Auto-generated method stub
		return customerDao.getInUserAssociations(decidezone_id);
	}

	// 1,2,3,4
	public void assignedCustomerToDecidedZone(String customerids, String decidedZone_id) {
		// 给客户 重新关联定区 先解除之前用户定区绑定 再重新绑定
		// 先取消所有定区关联客户
		customerDao.cancleDecidedZoneCustomers(decidedZone_id);
		if ("tps".equalsIgnoreCase(customerids)) {
			return;// tps表示没有选择客户ids信息提交
		}
		// 重新绑定
		if (StringUtils.isNoneBlank(customerids)) {
			String customerIds[] = customerids.split(",");
			for (String id : customerIds) {
				customerDao.assignedCustomerToDecidedZone(id, decidedZone_id);
			}
		}

	}

	@Override
	public Customer findCustomerByTelephone(String telephone) {
		// hibernate ...
		return customerDao.findCustomerByTelephone(telephone);
	}

	@Override
	public Customer save(Customer customer) {
		// TODO Auto-generated method stub
		return customerDao.save(customer);
	}

	@Override
	public void updateadressbyid(String customerid, String address) {
		// TODO Auto-generated method stub
		customerDao.updateadressbyid(customerid, address);
		customerDao.setDecidedzoneNull(customerid);// 将定区Id置空 因为客户地址发生变化,需要重新定区关联客户
	}

	@Override
	public Customer findCustomerByAddress(String address) {
		// TODO Auto-generated method stub
		return customerDao.findCustomerByAddress(address);
	}

}
