package cn.itcast.dao.product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.domain.product.ProductEarningRate;

public interface IProductRateDAO extends JpaRepository<ProductEarningRate, Integer> {
	
	@Query("select per from ProductEarningRate per where per.productId=?1 order by per.month")
	List<ProductEarningRate> findByProductId(int parseInt);

	@Modifying
	@Query("delete from ProductEarningRate per where per.productId=?1")
	void delByProId(int proId);
	
	
	

}
