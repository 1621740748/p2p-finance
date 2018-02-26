package cn.itcast.dao.product;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.domain.product.Product;

public interface IProductDAO extends JpaRepository<Product, Long>{

}
