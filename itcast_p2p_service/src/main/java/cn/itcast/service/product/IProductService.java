package cn.itcast.service.product;

import java.util.List;

import cn.itcast.domain.product.Product;
import cn.itcast.domain.product.ProductEarningRate;

public interface IProductService {

	List<Product> findAll();
	public Product findById(Long proId);
	List<ProductEarningRate> findRateByProId(String proId);
	void update(Product product);

}
