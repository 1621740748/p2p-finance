package cn.itcast.dao.creditor;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.domain.creditor.CreditorModel;

public interface ICreditorDAO extends JpaRepository<CreditorModel, Integer> {

	@Query("select cm from CreditorModel cm where cm.debtStatus=11302 and cm.matchedStatus in(11401,11403)")
	List<CreditorModel> findMatchCreditor();

	@Modifying
	@Query("update CreditorModel cm set cm.availableMoney=?2 ,cm.matchedStatus=?4,cm.matchedMoney=?3 where cm.id=?1")
	void updateCreditorStatus(Integer id, double availableMoney, double matchedMoney, int status);

}
