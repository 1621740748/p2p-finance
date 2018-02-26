package cn.itcast.dao.debtormodel;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.domain.debtorrecord.DebtorModel;

public interface IDebtormodelDAO extends JpaRepository<DebtorModel, Integer> {

}
