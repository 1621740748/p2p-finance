package cn.itcast.service.productRate.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.dao.product.IProductRateDAO;
import cn.itcast.domain.product.ProductEarningRate;
import cn.itcast.service.productRate.IProductRateService;

@Service
public class ProductRateServiceImpl implements IProductRateService{

	@Autowired
	private IProductRateDAO productRateDao;

	@Override
	public List<ProductEarningRate> findByProId(String pid) {
		return productRateDao.findByProductId(Integer.parseInt(pid));
	}
}
