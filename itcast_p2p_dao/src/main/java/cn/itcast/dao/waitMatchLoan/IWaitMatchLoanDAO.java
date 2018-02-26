package cn.itcast.dao.waitMatchLoan;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.domain.po.waitmatchloan.WaitMatchLoanPO;

public interface IWaitMatchLoanDAO extends JpaRepository<WaitMatchLoanPO, Integer> {

}
