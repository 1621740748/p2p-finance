package cn.itcast.dao.product;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.domain.admin.ProductAccount;

public interface IProductAccountDAO extends JpaRepository<ProductAccount, Long>{

}
