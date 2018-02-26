package cn.itcast.service.productRate;

import java.util.List;

import cn.itcast.domain.product.ProductEarningRate;

public interface IProductRateService {

	List<ProductEarningRate> findByProId(String pid);

}
