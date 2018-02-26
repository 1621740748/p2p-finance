package cn.itcast.dao.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.domain.admin.AdminModel;
import cn.itcast.domain.admin.WeigthRule;

public interface IWeigthRuleDAO extends JpaRepository<WeigthRule, Long>{

	public WeigthRule findByWeigthType(int i);

}
