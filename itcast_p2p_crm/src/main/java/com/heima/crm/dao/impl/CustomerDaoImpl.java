package com.heima.crm.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

import com.heima.crm.dao.CustomerDao;
import com.heima.crm.domain.Customer;

@Repository
@SuppressWarnings("all")
public class CustomerDaoImpl extends HibernateDaoSupport implements CustomerDao {

	@Autowired
	public void setXXXX(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	public List<Customer> getNoAssociations() {
		try {
			List<Customer> list = getHibernateTemplate().find("from Customer where decidedzoneId  is null");
			return list.isEmpty() ? null : list;
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public List<Customer> getInUserAssociations(String decidezone_id) {
		List<Customer> list = getHibernateTemplate().find("from Customer where decidedzoneId = ?", decidezone_id);
		return list.isEmpty() ? null : list;
	}

	public void assignedCustomerToDecidedZone(String customer_id, String decidedZone_id) {
		// 修改 update xxx set decidezoneid = ? where id = ? hql Query session
		getSession().createQuery("update Customer set decidedzoneId = ? where id = ?").setParameter(0, decidedZone_id)
				.setParameter(1, Integer.parseInt(customer_id)).executeUpdate();
	}

	// 取消 定区 关联所有用户
	public void cancleDecidedZoneCustomers(String decidedZone_id) {
		getSession().createQuery("update Customer set decidedzoneId = null where decidedzoneId = ?")
				.setParameter(0, decidedZone_id).executeUpdate();

	}

	@Override
	public Customer findCustomerByTelephone(String telephone) {
		List<Customer> list = getHibernateTemplate().find("from Customer where telephone = ?", telephone);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public Customer save(Customer customer) {
		Serializable id = getHibernateTemplate().save(customer);
		customer.setId((Integer) id);
		return customer;
	}

	@Override
	public void updateadressbyid(String customerid, String address) {
		getSession().createQuery("update Customer set address = ? where id = ?").setParameter(0, address)
				.setParameter(1, Integer.parseInt(customerid)).executeUpdate();

	}

	@Override
	public Customer findCustomerByAddress(String address) {
		List<Customer> list = getHibernateTemplate().find("from Customer where address = ?", address);
		return list.isEmpty() ? null : list.get(0);
	}

	@Override
	public void setDecidedzoneNull(String customerid) {
		getSession().createQuery("update Customer set decidedzoneId =null where id = ?")
				.setParameter(0, Integer.parseInt(customerid)).executeUpdate();
	}

}
