package cn.itcast.dao.bank;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.domain.bankCardInfo.Bank;

public interface IBankDAO extends JpaRepository<Bank, Integer>{

}
